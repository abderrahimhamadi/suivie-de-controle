package com.example.miniprojetasedsinpt.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class ResultatPrelevement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateBA;
    private int numeroBA;
    private boolean conforme;
    @ManyToOne
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Personne personne;
    @OneToOne
    private Prelevement prelevement;
    @OneToOne(mappedBy = "resultatPrel")
    private DetailNonConformite detailNonConformite;

}
