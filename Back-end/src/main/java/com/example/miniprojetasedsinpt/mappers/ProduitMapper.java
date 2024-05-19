package com.example.miniprojetasedsinpt.mappers;

import com.example.miniprojetasedsinpt.dtos.ProduitDTO;
import com.example.miniprojetasedsinpt.entities.Produit;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ProduitMapper {

    public ProduitDTO fromProduit(Produit produit) {
        ProduitDTO produitDTO = new ProduitDTO();
        BeanUtils.copyProperties(produit,produitDTO);
        return produitDTO;
    }

    public Produit fromProduitDTO(ProduitDTO produitDTO) {
        Produit produit = new Produit();
        BeanUtils.copyProperties(produitDTO,produit);
        return produit;
    }

}
