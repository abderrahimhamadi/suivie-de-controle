package com.example.miniprojetasedsinpt.services;

import com.example.miniprojetasedsinpt.dtos.ResultatPrelevementDTO;
import com.example.miniprojetasedsinpt.dtos.ResultatPrelevementResponseDTO;
import com.example.miniprojetasedsinpt.exceptions.*;

import java.util.List;

public interface ResultatPrelevementService {

    ResultatPrelevementDTO saveResultatPrelevement(ResultatPrelevementDTO resultatPrelevement) throws PersonneNotFoundException, ProduitNotFoundException, PrelevementNotFoundException, NomOrCategorieIsNullException;
    ResultatPrelevementDTO getResultatPrelevement(Long id) throws ResultatNotFoundException;
    void deleteResultatPrelevement(long id) throws ResultatNotFoundException;
    ResultatPrelevementResponseDTO getAllResultatPrelevement(int page, int size);
    ResultatPrelevementResponseDTO getAllResultatPrelevementByPersonne(Long idPersonne, String kw, String numeroBA, String conforme, int page, int size) throws PersonneNotFoundException;
    ResultatPrelevementResponseDTO getAllResultatPrelevementByPersonneAndPrelevement(
            Long idPersonne, String kw, String numeroBA, String conforme, int page, int size) throws PersonneNotFoundException;
}
