package com.example.miniprojetasedsinpt.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ResultatPrelevementResponseDTO {
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<ResultatPrelevementDTO> resultatPrelevementDTOS;
}
