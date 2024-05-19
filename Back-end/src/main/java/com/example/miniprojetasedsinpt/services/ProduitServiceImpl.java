package com.example.miniprojetasedsinpt.services;

import com.example.miniprojetasedsinpt.dtos.ProduitDTO;
import com.example.miniprojetasedsinpt.entities.Produit;
import com.example.miniprojetasedsinpt.exceptions.NomOrCategorieIsNullException;
import com.example.miniprojetasedsinpt.exceptions.ProduitNotFoundException;
import com.example.miniprojetasedsinpt.mappers.ProduitMapper;
import com.example.miniprojetasedsinpt.repositories.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService {
    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    @Override
    public ProduitDTO saveProduit(ProduitDTO produitDTO) throws NomOrCategorieIsNullException {
        if (produitDTO.getNom() == null || produitDTO.getCategorie() == null ||
                produitDTO.getNom().equals("") || produitDTO.getCategorie().equals("")) {
            throw new NomOrCategorieIsNullException("Le nom et le categorie sont requis");
        }
        Produit produit = produitMapper.fromProduitDTO(produitDTO);
        Produit savedProduit = produitRepository.save(produit);
        return produitMapper.fromProduit(savedProduit);
    }

    @Override
    public ProduitDTO getProduit(Long id) throws ProduitNotFoundException {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ProduitNotFoundException("Ce produit n'existe pas"));
        return produitMapper.fromProduit(produit);
    }

    @Override
    public List<ProduitDTO> getAllProduit() {
        List<Produit> produits = produitRepository.findAll();
        List<ProduitDTO> produitDTOS = produits.stream()
                .map(produit -> produitMapper.fromProduit(produit))
                .collect(Collectors.toList());
        return produitDTOS;
    }
}
