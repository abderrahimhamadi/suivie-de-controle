package com.example.miniprojetasedsinpt.repositories;

import com.example.miniprojetasedsinpt.entities.DetailNonConformite;
import com.example.miniprojetasedsinpt.entities.ResultatPrelevement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailNonConformiteRepository extends JpaRepository<DetailNonConformite, Long> {
    DetailNonConformite findByResultatPrel(ResultatPrelevement resultatPrelevement);
    DetailNonConformite findByResultatPrelId(Long idResultat);
}
