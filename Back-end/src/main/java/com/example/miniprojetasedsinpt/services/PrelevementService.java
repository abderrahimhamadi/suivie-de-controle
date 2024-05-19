package com.example.miniprojetasedsinpt.services;

import com.example.miniprojetasedsinpt.dtos.PrelevementDTO;
import com.example.miniprojetasedsinpt.dtos.PrelevementResponseDTO;
import com.example.miniprojetasedsinpt.entities.Prelevement;
import com.example.miniprojetasedsinpt.entities.utils.EtatAvancement;
import com.example.miniprojetasedsinpt.entities.utils.Labo;
import com.example.miniprojetasedsinpt.exceptions.NomOrCategorieIsNullException;
import com.example.miniprojetasedsinpt.exceptions.PersonneNotFoundException;
import com.example.miniprojetasedsinpt.exceptions.PrelevementNotFoundException;
import com.example.miniprojetasedsinpt.exceptions.ProduitNotFoundException;

import java.util.List;

public interface PrelevementService {
    PrelevementDTO savePrelevement(PrelevementDTO prelevementDTO) throws PersonneNotFoundException, ProduitNotFoundException, NomOrCategorieIsNullException;
    PrelevementDTO getPrelevement(Long id) throws PrelevementNotFoundException;
    void deletePrelevement(long id) throws PrelevementNotFoundException;
    PrelevementResponseDTO getAllPrelevement(String kw, EtatAvancement etat, int page, int size);
    /*PrelevementResponseDTO getAllPrelevementByPersonne
            (Long idPersonne,String kw, EtatAvancement etat, int page, int size) throws PersonneNotFoundException;*/

    PrelevementResponseDTO getAllPrelevementByPersonne
            (Long idPersonne, String kw, EtatAvancement etat, String numeroProcesVerbal, int page, int size)
            throws PersonneNotFoundException;

    PrelevementResponseDTO getAllPrelevementByLabo
            (Labo labo, String kw, EtatAvancement etat,String numeroProcesVerbal, int page, int size);
}
