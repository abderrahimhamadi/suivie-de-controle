package com.example.miniprojetasedsinpt.dtos;

import com.example.miniprojetasedsinpt.entities.utils.Labo;
import com.example.miniprojetasedsinpt.entities.utils.TypePersonne;
import lombok.Data;

@Data
public class PersonneDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private TypePersonne type;
    private Labo labo;
}
