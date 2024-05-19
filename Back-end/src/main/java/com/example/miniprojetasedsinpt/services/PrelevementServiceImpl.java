package com.example.miniprojetasedsinpt.services;

import com.example.miniprojetasedsinpt.dtos.PersonneDTO;
import com.example.miniprojetasedsinpt.dtos.PrelevementDTO;
import com.example.miniprojetasedsinpt.dtos.PrelevementResponseDTO;
import com.example.miniprojetasedsinpt.dtos.ProduitDTO;
import com.example.miniprojetasedsinpt.entities.Personne;
import com.example.miniprojetasedsinpt.entities.Prelevement;
import com.example.miniprojetasedsinpt.entities.Produit;
import com.example.miniprojetasedsinpt.entities.utils.EtatAvancement;
import com.example.miniprojetasedsinpt.entities.utils.Labo;
import com.example.miniprojetasedsinpt.exceptions.NomOrCategorieIsNullException;
import com.example.miniprojetasedsinpt.exceptions.PersonneNotFoundException;
import com.example.miniprojetasedsinpt.exceptions.PrelevementNotFoundException;
import com.example.miniprojetasedsinpt.exceptions.ProduitNotFoundException;
import com.example.miniprojetasedsinpt.mappers.PersonneMapper;
import com.example.miniprojetasedsinpt.mappers.PrelevementMapper;
import com.example.miniprojetasedsinpt.mappers.ProduitMapper;
import com.example.miniprojetasedsinpt.repositories.PrelevementRepository;
import com.example.miniprojetasedsinpt.repositories.ProduitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrelevementServiceImpl implements PrelevementService {
    private final PrelevementRepository prelevementrepository;
    private final PrelevementMapper prelevementMapper;
    private final PersonneService personneService;
    private final PersonneMapper personneMapper;
    private final ProduitMapper produitMapper;
    private final ProduitService produitService;
    private final ProduitRepository produitRepository;

    @Override
    public PrelevementDTO savePrelevement(PrelevementDTO prelevementDTO)
            throws PersonneNotFoundException, ProduitNotFoundException, NomOrCategorieIsNullException {

        String produitNom = prelevementDTO.getProduitDTO().getNom();
        String produitCategorie = prelevementDTO.getProduitDTO().getCategorie();

        Produit savedProduit = produitRepository.findByNomAndAndCategorie(produitNom, produitCategorie);
        ProduitDTO savedProduitDTO = null;

        if (savedProduit == null) {
            savedProduitDTO = produitService.saveProduit(prelevementDTO.getProduitDTO());
        } else {
            savedProduitDTO = produitMapper.fromProduit(savedProduit);
        }

        /*log.info("Produit");
        log.info(String.valueOf(savedProduitDTO.getId()));*/

        Prelevement prelevement = prelevementMapper.fromPrelevementDTO(prelevementDTO);
        prelevement.setProduit(produitMapper.fromProduitDTO(savedProduitDTO));
        Prelevement savedPrelevement = prelevementrepository.save(prelevement);
        return prelevementMapper.fromPrelevement(savedPrelevement);
    }

    @Override
    public PrelevementDTO getPrelevement(Long id) throws PrelevementNotFoundException {
        Prelevement prelevement = prelevementrepository.findById(id)
                .orElseThrow(() -> new PrelevementNotFoundException("Ce prelevement n'existe pas"));
        return prelevementMapper.fromPrelevement(prelevement);
    }

    @Override
    public void deletePrelevement(long id) throws PrelevementNotFoundException {
        prelevementrepository.findById(id).orElseThrow(() ->
                new PrelevementNotFoundException("Ce prelevement n'existe pas"));
        prelevementrepository.deleteById(id);
    }

    @Override
    public PrelevementResponseDTO getAllPrelevement(String kw, EtatAvancement etat, int page, int size) {
        Page<Prelevement> prelevementPages = null;
        if (etat == null || etat.equals("")) {
            prelevementPages = prelevementrepository.findByProduitNomContainsOrderByDateEnvoieDesc(kw, PageRequest.of(page, size));
        } else {
            prelevementPages = prelevementrepository.findByProduitNomContainsAndEtatAvancementOrderByDateEnvoieDesc(kw, etat, PageRequest.of(page, size));
        }

        List<PrelevementDTO> prelevementDTOS = prelevementPages.stream()
                .map(prel -> prelevementMapper.fromPrelevement(prel))
                .collect(Collectors.toList());

        PrelevementResponseDTO prelevementResponseDTO = new PrelevementResponseDTO();
        prelevementResponseDTO.setPrelevementDTOS(prelevementDTOS);
        prelevementResponseDTO.setCurrentPage(page);
        prelevementResponseDTO.setTotalPages(prelevementPages.getTotalPages());
        prelevementResponseDTO.setPageSize(prelevementPages.getSize());

        return prelevementResponseDTO;
    }

    /*@Override
    public PrelevementResponseDTO getAllPrelevementByPersonne
            (Long idPersonne,String kw, EtatAvancement etat, int page, int size)
            throws PersonneNotFoundException {

        PersonneDTO personneDTO = personneService.getPersonne(idPersonne);
        Personne personne = personneMapper.fromPersonneDTO(personneDTO);

        *//*Page<Prelevement> prelevementPages =
                prelevementrepository.findByPersonne(personne, PageRequest.of(page, size));*//*
        Page<Prelevement> prelevementPages = null;
        if (etat == null || etat.equals("")) {
            prelevementPages = prelevementrepository.findByPersonneAndProduitNomContainsOrderByDateEnvoieDesc(
                    personne, kw, PageRequest.of(page, size));
        } else {
            prelevementPages= prelevementrepository.findByPersonneAndProduitNomContainsAndEtatAvancementOrderByDateEnvoieDesc(
                    personne, kw, etat, PageRequest.of(page, size));
        }


        List<PrelevementDTO> prelevementDTOS = prelevementPages.stream()
                .map(prelevement -> prelevementMapper.fromPrelevement(prelevement))
                .collect(Collectors.toList());

        PrelevementResponseDTO prelevementResponseDTO = new PrelevementResponseDTO();
        prelevementResponseDTO.setPrelevementDTOS(prelevementDTOS);
        prelevementResponseDTO.setCurrentPage(page);
        prelevementResponseDTO.setPageSize(prelevementPages.getSize());
        prelevementResponseDTO.setTotalPages(prelevementPages.getTotalPages());

        return prelevementResponseDTO;
    }*/
    @Override
    public PrelevementResponseDTO getAllPrelevementByPersonne(
            Long idPersonne, String kw, EtatAvancement etat, String numeroProcesVerbal, int page, int size
    ) throws PersonneNotFoundException {

        PersonneDTO personneDTO = personneService.getPersonne(idPersonne);
        Personne personne = personneMapper.fromPersonneDTO(personneDTO);

        Integer numero = null;
        if (numeroProcesVerbal != null && !numeroProcesVerbal.equals("")) {
            numero = Integer.parseInt(numeroProcesVerbal);
        }

        Page<Prelevement> prelevementPages = null;
        if ((etat == null || etat.equals("")) && (numeroProcesVerbal == null || numeroProcesVerbal.equals(""))) {
            prelevementPages = prelevementrepository.findByPersonneAndProduitNomContainsOrderByDateEnvoieDesc(
                    personne, kw, PageRequest.of(page, size));
        } else if (numeroProcesVerbal == null || numeroProcesVerbal.equals("")){
            prelevementPages= prelevementrepository.findByPersonneAndProduitNomContainsAndEtatAvancementOrderByDateEnvoieDesc(
                    personne, kw, etat, PageRequest.of(page, size));
        } else if (etat == null || etat.equals("")) {
            prelevementPages= prelevementrepository.findByPersonneAndProduitNomContainsAndNumeroProcesVerbalOrderByDateEnvoieDesc(
                    personne, kw, numero, PageRequest.of(page, size)
            );
        } else {
            prelevementPages= prelevementrepository.findByPersonneAndProduitNomContainsAndEtatAvancementAndNumeroProcesVerbalOrderByDateEnvoieDesc(
              personne, kw, etat, numero,PageRequest.of(page, size)
            );
        }


        List<PrelevementDTO> prelevementDTOS = prelevementPages.stream()
                .map(prelevement -> prelevementMapper.fromPrelevement(prelevement))
                .collect(Collectors.toList());

        PrelevementResponseDTO prelevementResponseDTO = new PrelevementResponseDTO();
        prelevementResponseDTO.setPrelevementDTOS(prelevementDTOS);
        prelevementResponseDTO.setCurrentPage(page);
        prelevementResponseDTO.setPageSize(prelevementPages.getSize());
        prelevementResponseDTO.setTotalPages(prelevementPages.getTotalPages());

        return prelevementResponseDTO;
    }

    @Override
    public PrelevementResponseDTO getAllPrelevementByLabo(
            Labo labo, String kw, EtatAvancement etat, String numeroProcesVerbal, int page, int size
    ) {
        Integer numero = null;
        if (numeroProcesVerbal != null && !numeroProcesVerbal.equals("")) {
            numero = Integer.parseInt(numeroProcesVerbal);
        }

        Page<Prelevement> prelevementPages = null;
        if ((etat == null || etat.equals("")) && (numeroProcesVerbal == null || numeroProcesVerbal.equals(""))) {
            prelevementPages = prelevementrepository.findByLaboDestinationAndProduitNomContainsOrderByDateEnvoieDesc(
                    labo, kw, PageRequest.of(page, size));
        } else if (etat == null || etat.equals("")) {
            prelevementPages = prelevementrepository.findByLaboDestinationAndProduitNomContainsAndNumeroProcesVerbalOrderByDateEnvoieDesc(
                    labo, kw, numero, PageRequest.of(page, size)
            );
        } else if (numeroProcesVerbal == null || numeroProcesVerbal.equals("")) {
            prelevementPages = prelevementrepository.findByLaboDestinationAndProduitNomContainsAndEtatAvancementOrderByDateEnvoieDesc(
                    labo, kw, etat, PageRequest.of(page, size)
            );
        } else {
            prelevementPages= prelevementrepository.findByLaboDestinationAndProduitNomContainsAndEtatAvancementAndNumeroProcesVerbalOrderByDateEnvoieDesc(
                    labo, kw, etat, numero, PageRequest.of(page, size)
            );
        }

        List<PrelevementDTO> prelevementDTOS = prelevementPages.stream()
                .map(prelevement -> prelevementMapper.fromPrelevement(prelevement))
                .collect(Collectors.toList());

        PrelevementResponseDTO prelevementResponseDTO = new PrelevementResponseDTO();
        prelevementResponseDTO.setPrelevementDTOS(prelevementDTOS);
        prelevementResponseDTO.setCurrentPage(page);
        prelevementResponseDTO.setPageSize(prelevementPages.getSize());
        prelevementResponseDTO.setTotalPages(prelevementPages.getTotalPages());
        return prelevementResponseDTO;
    }
}
