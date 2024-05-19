package com.example.miniprojetasedsinpt.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String categorie;
    private String nom;
    @OneToMany
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Prelevement> prelevement;
}
