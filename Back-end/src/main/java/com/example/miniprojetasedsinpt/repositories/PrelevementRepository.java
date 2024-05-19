package com.example.miniprojetasedsinpt.repositories;

import com.example.miniprojetasedsinpt.entities.Personne;
import com.example.miniprojetasedsinpt.entities.Prelevement;
import com.example.miniprojetasedsinpt.entities.ResultatPrelevement;
import com.example.miniprojetasedsinpt.entities.utils.EtatAvancement;
import com.example.miniprojetasedsinpt.entities.utils.Labo;
import org.hibernate.annotations.OrderBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrelevementRepository extends JpaRepository<Prelevement, Long> {
    List<Prelevement>findByPersonne(Personne personne);
    Page<Prelevement> findByPersonne(Personne personne, Pageable pageable);
    Page<Prelevement> findByProduitNomContainsOrderByDateEnvoieDesc(String keyword, Pageable pageable);
    Page<Prelevement> findByEtatAvancementOrderByDateEnvoieDesc(EtatAvancement etatAvancement, Pageable pageable);
    Page<Prelevement> findByProduitNomContainsAndEtatAvancementOrderByDateEnvoieDesc(
            String keyword, EtatAvancement etatAvancement, Pageable pageable
    );
    Page<Prelevement> findByPersonneAndProduitNomContainsAndEtatAvancementOrderByDateEnvoieDesc(
            Personne personne, String kw, EtatAvancement etatAvancement, Pageable pageable);
    Page<Prelevement> findByPersonneAndProduitNomContainsOrderByDateEnvoieDesc(
            Personne personne, String kw, Pageable pageable
    );
    Page<Prelevement> findByPersonneAndProduitNomContainsAndNumeroProcesVerbalOrderByDateEnvoieDesc(
            Personne personne, String kw, int numeroProcesVerbal, Pageable pageable
    );
    Page<Prelevement> findByPersonneAndProduitNomContainsAndEtatAvancementAndNumeroProcesVerbalOrderByDateEnvoieDesc(
            Personne personne, String kw, EtatAvancement etatAvancement,
            int numeroProcesVerbal, Pageable pageable
    );

    Page<Prelevement> findByLaboDestinationAndProduitNomContainsOrderByDateEnvoieDesc(
            Labo labo, String kw, Pageable pageable
    );
    Page<Prelevement> findByLaboDestinationAndProduitNomContainsAndEtatAvancementOrderByDateEnvoieDesc(
            Labo labo, String kw, EtatAvancement etatAvancement, Pageable pageable
    );
    Page<Prelevement> findByLaboDestinationAndProduitNomContainsAndNumeroProcesVerbalOrderByDateEnvoieDesc(
            Labo labo, String kw, int numeroProcesVerbal, Pageable pageable
    );
    Page<Prelevement> findByLaboDestinationAndProduitNomContainsAndEtatAvancementAndNumeroProcesVerbalOrderByDateEnvoieDesc(
            Labo labo, String kw, EtatAvancement etatAvancement,
            int numeroProcesVerbal, Pageable pageable
    );

    Prelevement findByResultatPrel(ResultatPrelevement resultatPrelevement);
}
