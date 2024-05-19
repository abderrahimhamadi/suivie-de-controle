package com.example.miniprojetasedsinpt.repositories;

import com.example.miniprojetasedsinpt.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Produit findByNomAndAndCategorie(String nom, String categorie);
}
