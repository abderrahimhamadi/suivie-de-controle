package com.example.miniprojetasedsinpt.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ResultatPrelevementDTO {
    private Long id;
    private Date dateBA;
    private int numeroBA;
    private boolean conforme;
    private Long idPersonne;
    private Long idPrelevement;
    private String nomProduit;
}
