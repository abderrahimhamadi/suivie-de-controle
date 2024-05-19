package com.example.miniprojetasedsinpt.dtos;

import lombok.Data;

@Data
public class DetailNonConformiteDTO {
    private Long id;
    private String dateTA;
    private String numeroTA;
    private String detail;
    private Long idResultat;
}
