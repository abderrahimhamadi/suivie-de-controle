package com.example.miniprojetasedsinpt.services;

import com.example.miniprojetasedsinpt.dtos.ProduitDTO;
import com.example.miniprojetasedsinpt.exceptions.NomOrCategorieIsNullException;
import com.example.miniprojetasedsinpt.exceptions.ProduitNotFoundException;

import java.util.List;

public interface ProduitService {

    ProduitDTO saveProduit(ProduitDTO produitDTO) throws NomOrCategorieIsNullException;
    ProduitDTO getProduit(Long id) throws ProduitNotFoundException;
    List<ProduitDTO> getAllProduit();
}
