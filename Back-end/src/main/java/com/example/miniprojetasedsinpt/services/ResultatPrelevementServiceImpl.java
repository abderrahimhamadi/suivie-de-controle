package com.example.miniprojetasedsinpt.services;

import com.example.miniprojetasedsinpt.dtos.PersonneDTO;
import com.example.miniprojetasedsinpt.dtos.PrelevementDTO;
import com.example.miniprojetasedsinpt.dtos.ResultatPrelevementDTO;
import com.example.miniprojetasedsinpt.dtos.ResultatPrelevementResponseDTO;
import com.example.miniprojetasedsinpt.entities.DetailNonConformite;
import com.example.miniprojetasedsinpt.entities.Personne;
import com.example.miniprojetasedsinpt.entities.Prelevement;
import com.example.miniprojetasedsinpt.entities.ResultatPrelevement;
import com.example.miniprojetasedsinpt.entities.utils.EtatAvancement;
import com.example.miniprojetasedsinpt.exceptions.*;
import com.example.miniprojetasedsinpt.mappers.PersonneMapper;
import com.example.miniprojetasedsinpt.mappers.ResultatPrelevementMapper;
import com.example.miniprojetasedsinpt.repositories.DetailNonConformiteRepository;
import com.example.miniprojetasedsinpt.repositories.PrelevementRepository;
import com.example.miniprojetasedsinpt.repositories.ResultatPrelevementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultatPrelevementServiceImpl implements ResultatPrelevementService {
    private final ResultatPrelevementRepository resultatPrelevementRepository;
    private final ResultatPrelevementMapper resultatPrelevementMapper;
    private final PersonneService personneService;
    private final PersonneMapper personneMapper;
    private final PrelevementService prelevementService;
    private final PrelevementRepository prelevementRepository;
    private final DetailNonConformiteRepository detailNonConformiteRepository;

    @Override
    public ResultatPrelevementDTO saveResultatPrelevement(ResultatPrelevementDTO resultatPrelevementDTO)
            throws PersonneNotFoundException, ProduitNotFoundException, PrelevementNotFoundException, NomOrCategorieIsNullException {
        PrelevementDTO prelevement = prelevementService.getPrelevement(resultatPrelevementDTO.getIdPrelevement());
        prelevement.setEtatAvancement(EtatAvancement.TERMINEE);
        prelevementService.savePrelevement(prelevement);
        ResultatPrelevement resultatPrelevement =
                resultatPrelevementMapper.fromResultatPrelevementDTO(resultatPrelevementDTO);
        ResultatPrelevement savedResultat = resultatPrelevementRepository.save(resultatPrelevement);
        return resultatPrelevementMapper.fromResultatPrelevement(savedResultat);
    }

    @Override
    public ResultatPrelevementDTO getResultatPrelevement(Long id)
            throws ResultatNotFoundException
    {
        ResultatPrelevement resultatPrelevement =
                resultatPrelevementRepository.findById(id).orElseThrow(() ->
                new ResultatNotFoundException("Ce resultat de prelevement n'existe pas"));
        return resultatPrelevementMapper.fromResultatPrelevement(resultatPrelevement);
    }

    @Override
    public void deleteResultatPrelevement(long id) throws ResultatNotFoundException {
        ResultatPrelevement resultat = resultatPrelevementRepository.findById(id).orElseThrow(() ->
            new ResultatNotFoundException("Ce resultat de  prelevement n'existe pas")
        );
        Prelevement prelevement = prelevementRepository.findByResultatPrel(resultat);
        prelevement.setEtatAvancement(EtatAvancement.EN_INSTANCE);
        prelevementRepository.save(prelevement);

        DetailNonConformite detail = detailNonConformiteRepository.findByResultatPrel(resultat);
        if (detail != null) {
            detailNonConformiteRepository.delete(detail);
        }

        resultatPrelevementRepository.deleteById(id);
    }

    @Override
    public ResultatPrelevementResponseDTO getAllResultatPrelevement(int page, int size) {
        Page<ResultatPrelevement> resultatPages =
                resultatPrelevementRepository.findAll(PageRequest.of(page, size));
        List<ResultatPrelevementDTO> resultatPrelevementDTOS = resultatPages.stream()
                .map(resultat -> resultatPrelevementMapper.fromResultatPrelevement(resultat))
                .collect(Collectors.toList());
        ResultatPrelevementResponseDTO resultatPrelevementResponseDTO = new ResultatPrelevementResponseDTO();
        resultatPrelevementResponseDTO.setResultatPrelevementDTOS(resultatPrelevementDTOS);
        resultatPrelevementResponseDTO.setCurrentPage(page);
        resultatPrelevementResponseDTO.setTotalPages(resultatPages.getTotalPages());
        resultatPrelevementResponseDTO.setPageSize(size);

        return resultatPrelevementResponseDTO;
    }

    @Override
    public ResultatPrelevementResponseDTO getAllResultatPrelevementByPersonne(
            Long idPersonne, String kw, String numeroBA, String conforme, int page, int size) throws PersonneNotFoundException {

        Personne personne = personneMapper.fromPersonneDTO(personneService.getPersonne(idPersonne));

        Integer numero = null;
        if (numeroBA != null && !numeroBA.equals("")) {
            log.info("numero: " +numeroBA);
            numero = Integer.parseInt(numeroBA);
        }

        Boolean conformité = null ;
        if (conforme != null && !conforme.equals("")){
            log.info("conforme "+ conforme);
            if (conforme.equals("Oui")) {
                log.info("conforme_Oui");
                conformité= true;
            } else {
                log.info("conforme_Non");
                conformité= false;
            }
        }

        Page<ResultatPrelevement> resultatPages = null;
        if ((numeroBA == null || numeroBA.equals("")) && (conforme == null || conforme.equals(""))) {
            log.info("1");
            resultatPages = resultatPrelevementRepository.findByPersonneAndPrelevement_Produit_NomContains(
                    personne, kw, PageRequest.of(page, size)
            );
        } else if (numeroBA == null || numeroBA.equals("")) {
            log.info("2");
            resultatPages = resultatPrelevementRepository.findByPersonneAndPrelevement_Produit_NomContainsAndConforme(
                    personne, kw, conformité, PageRequest.of(page, size)
            );
        } else if (conforme == null || conforme.equals("")) {
            log.info("3");
            resultatPages = resultatPrelevementRepository.findByPersonneAndPrelevement_Produit_NomContainsAndNumeroBA(
                    personne, kw, numero, PageRequest.of(page, size)
            );
        } else {
            log.info("4");
            resultatPages = resultatPrelevementRepository.findByPersonneAndPrelevement_Produit_NomContainsAndNumeroBAAndConforme(
                    personne, kw, numero, conformité, PageRequest.of(page, size)
            );
        }

        List<ResultatPrelevementDTO> resultatPrelevementDTOS = resultatPages.stream()
                .map(resultat -> {
                    ResultatPrelevementDTO resultatPrelevement =
                            resultatPrelevementMapper.fromResultatPrelevement(resultat);
                    PrelevementDTO prelevement = null;
                    try {
                        prelevement = prelevementService.getPrelevement(resultatPrelevement.getIdPrelevement());
                    } catch (PrelevementNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    resultatPrelevement.setNomProduit(prelevement.getProduitDTO().getNom());
                    return resultatPrelevement;
                })
                .collect(Collectors.toList());

        ResultatPrelevementResponseDTO resultatPrelevementResponseDTO = new ResultatPrelevementResponseDTO();
        resultatPrelevementResponseDTO.setResultatPrelevementDTOS(resultatPrelevementDTOS);
        resultatPrelevementResponseDTO.setCurrentPage(page);
        resultatPrelevementResponseDTO.setTotalPages(resultatPages.getTotalPages());
        resultatPrelevementResponseDTO.setPageSize(size);

        return resultatPrelevementResponseDTO;
    }

    @Override
    public ResultatPrelevementResponseDTO getAllResultatPrelevementByPersonneAndPrelevement(
            Long idPersonne, String kw, String numeroBA, String conforme, int page, int size) throws PersonneNotFoundException {

        var ref1 = new Object() {
            Integer numero = null;
        };
        if (numeroBA != null && !numeroBA.equals("")) {
            ref1.numero = Integer.parseInt(numeroBA);
        }

        var ref = new Object() {
            Boolean conformité = null;
        };
        if (conforme != null && !conforme.equals("")){
            if (conforme.equals("Oui")) {
                ref.conformité = true;
            } else {
                ref.conformité = false;
            }
        }

        PersonneDTO personneDTO = personneService.getPersonne(idPersonne);
        List<Prelevement> prelevements = prelevementRepository
                .findByPersonne(personneMapper.fromPersonneDTO(personneDTO));

        List<ResultatPrelevement> resultats = null;
        if ((numeroBA == null || numeroBA.equals("")) && (conforme == null || conforme.equals(""))) {
            resultats = prelevements.stream()
                    .map(prelevement -> resultatPrelevementRepository.findByPrelevementAndPrelevement_Produit_NomContains(
                            prelevement,kw)
                    )
                    .collect(Collectors.toList());
        } else if (numeroBA == null || numeroBA.equals("")) {
            resultats = prelevements.stream()
                    .map(prelevement -> resultatPrelevementRepository.findByPrelevementAndPrelevement_Produit_NomContainsAndConforme(
                                prelevement,kw, ref.conformité)
                    )
                    .collect(Collectors.toList());
        } else if (conforme == null || conforme.equals("")) {
            resultats = prelevements.stream()
                    .map(prelevement -> resultatPrelevementRepository.findByPrelevementAndPrelevement_Produit_NomContainsAndNumeroBA(
                            prelevement, kw, ref1.numero)
                    )
                    .collect(Collectors.toList());
        } else {
            resultats = prelevements.stream()
                    .map(prelevement -> resultatPrelevementRepository.findByPrelevementAndPrelevement_Produit_NomContainsAndNumeroBAAndConforme(
                            prelevement, kw, ref1.numero, ref.conformité)
                    )
                    .collect(Collectors.toList());
        }

        List<ResultatPrelevementDTO> resultatPrelevementDTOS = resultats.stream()
                .map(resultatPrelevement -> {
                    if (resultatPrelevement != null) {
                        return resultatPrelevementMapper.fromResultatPrelevement(resultatPrelevement);
                    }
                    return null;
                })
                .filter(resultatPrelevementDTO -> resultatPrelevementDTO != null)
                .collect(Collectors.toList());

        int listSize = resultatPrelevementDTOS.size();
        ResultatPrelevementResponseDTO resultatPrelevementResponseDTO = new ResultatPrelevementResponseDTO();
        if (listSize<size && page == 0) {
            resultatPrelevementResponseDTO.setResultatPrelevementDTOS(resultatPrelevementDTOS);
        } else if (listSize<size && page != 0) {
            resultatPrelevementResponseDTO.setResultatPrelevementDTOS(null);
        } else if (page*size>=listSize) {
            resultatPrelevementResponseDTO.setResultatPrelevementDTOS(null);
        } else if ((page+1)*size>listSize-1) {
            resultatPrelevementResponseDTO.setResultatPrelevementDTOS(resultatPrelevementDTOS.subList(page*size,listSize));
        }else {
            resultatPrelevementResponseDTO.setResultatPrelevementDTOS(resultatPrelevementDTOS.subList(page*size,(page+1)*size));
        }

        int totalPages;
        if (listSize%size==0) {
            totalPages=listSize/size;
        } else if (size>listSize) {
            totalPages=1;
        } else {
            totalPages=listSize/size + 1;
        }

        resultatPrelevementResponseDTO.setCurrentPage(page);
        resultatPrelevementResponseDTO.setTotalPages(totalPages);
        resultatPrelevementResponseDTO.setPageSize(size);
        return resultatPrelevementResponseDTO;
    }

}
