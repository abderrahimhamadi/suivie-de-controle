package com.example.miniprojetasedsinpt.dtos;

import com.example.miniprojetasedsinpt.entities.utils.*;
import lombok.Data;

import java.util.Date;

@Data
public class PrelevementDTO {
    private Long id;
    private Date dateProcesVerbal;
    private int numeroProcesVerbal;
    private TypePrelevement typePrelevement;
    private Cadre cadreControle;
    private Niveau niveauPrel;
    private Labo laboDestination;
    private Date dateEnvoie;
    private EtatAvancement etatAvancement;
    private Long idPersonne;
    //private Long idProduit;
    private ProduitDTO produitDTO;

}
