package com.example.miniprojetasedsinpt.repositories;

import com.example.miniprojetasedsinpt.entities.Personne;
import com.example.miniprojetasedsinpt.entities.Prelevement;
import com.example.miniprojetasedsinpt.entities.ResultatPrelevement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultatPrelevementRepository extends JpaRepository<ResultatPrelevement, Long> {
    Page<ResultatPrelevement> findByPersonne(Personne personne, Pageable pageable);
    Page<ResultatPrelevement> findByPersonneAndPrelevement_Produit_NomContains(
            Personne personne, String kw, Pageable pageable
    );
    Page<ResultatPrelevement> findByPersonneAndPrelevement_Produit_NomContainsAndNumeroBA(
            Personne personne, String kw, int numeroBA, Pageable pageable
    );
    Page<ResultatPrelevement> findByPersonneAndPrelevement_Produit_NomContainsAndConforme(
            Personne personne, String kw, boolean conforme, Pageable pageable
    );
    Page<ResultatPrelevement> findByPersonneAndPrelevement_Produit_NomContainsAndNumeroBAAndConforme(
            Personne personne, String kw, int numeroBA, boolean conforme, Pageable pageable
    );

    ResultatPrelevement findByPrelevementAndPrelevement_Produit_NomContains(
            Prelevement prelevement, String kw
    );
    ResultatPrelevement findByPrelevementAndPrelevement_Produit_NomContainsAndNumeroBA(
            Prelevement prelevement, String kw, int numeroBA
    );
    ResultatPrelevement findByPrelevementAndPrelevement_Produit_NomContainsAndConforme(
            Prelevement prelevement, String kw, boolean conforme
    );
    ResultatPrelevement findByPrelevementAndPrelevement_Produit_NomContainsAndNumeroBAAndConforme(
            Prelevement prelevement, String kw, int numeroBA, boolean conforme
    );
}
