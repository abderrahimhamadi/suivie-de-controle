package com.example.miniprojetasedsinpt.mappers;

import com.example.miniprojetasedsinpt.dtos.ResultatPrelevementDTO;
import com.example.miniprojetasedsinpt.entities.Personne;
import com.example.miniprojetasedsinpt.entities.Prelevement;
import com.example.miniprojetasedsinpt.entities.ResultatPrelevement;
import com.example.miniprojetasedsinpt.exceptions.PersonneNotFoundException;
import com.example.miniprojetasedsinpt.exceptions.PrelevementNotFoundException;
import com.example.miniprojetasedsinpt.exceptions.ProduitNotFoundException;
import com.example.miniprojetasedsinpt.services.PersonneService;
import com.example.miniprojetasedsinpt.services.PrelevementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultatPrelevementMapper {
    private final PersonneService personneService;
    private final PrelevementService prelevementService;
    private final PersonneMapper personneMapper;
    private final PrelevementMapper prelevementMapper;

    public ResultatPrelevementDTO fromResultatPrelevement(ResultatPrelevement resultatPrelevement) {
        ResultatPrelevementDTO resultatPrelevementDTO = new ResultatPrelevementDTO();
        BeanUtils.copyProperties(resultatPrelevement, resultatPrelevementDTO);
        resultatPrelevementDTO.setIdPrelevement(resultatPrelevement.getPrelevement().getId());
        resultatPrelevementDTO.setIdPersonne(resultatPrelevement.getPersonne().getId());
        resultatPrelevementDTO.setNomProduit(resultatPrelevement.getPrelevement().getProduit().getNom());
        return resultatPrelevementDTO;
    }

    public ResultatPrelevement fromResultatPrelevementDTO(ResultatPrelevementDTO resultatPrelevementDTO)
            throws PersonneNotFoundException, PrelevementNotFoundException, ProduitNotFoundException {
        ResultatPrelevement resultatPrelevement = new ResultatPrelevement();
        BeanUtils.copyProperties(resultatPrelevementDTO, resultatPrelevement);
        Personne personne = personneMapper.fromPersonneDTO(
                personneService.getPersonne(resultatPrelevementDTO.getIdPersonne()));
        resultatPrelevement.setPersonne(personne);
        Prelevement prelevement = prelevementMapper.fromPrelevementDTO(
                prelevementService.getPrelevement(resultatPrelevementDTO.getIdPrelevement())
        );
        resultatPrelevement.setPrelevement(prelevement);
        return resultatPrelevement;
    }

}
