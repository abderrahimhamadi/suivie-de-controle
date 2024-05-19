package com.example.miniprojetasedsinpt.dtos;

import com.example.miniprojetasedsinpt.entities.utils.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PrelevementResponseDTO {
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<PrelevementDTO> prelevementDTOS;
}
