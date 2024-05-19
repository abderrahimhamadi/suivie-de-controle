package com.example.miniprojetasedsinpt.services;

import com.example.miniprojetasedsinpt.dtos.DetailNonConformiteDTO;
import com.example.miniprojetasedsinpt.entities.DetailNonConformite;
import com.example.miniprojetasedsinpt.exceptions.*;

import java.util.List;

public interface DetailNonConformiteService {
    DetailNonConformiteDTO saveDetail(DetailNonConformiteDTO detail) throws ResultatNotFoundException, PersonneNotFoundException, ProduitNotFoundException, PrelevementNotFoundException;
    DetailNonConformiteDTO getDetail(Long id) throws DetailNotFoundException;
    List<DetailNonConformiteDTO> getAllDetails();
    DetailNonConformiteDTO getDetailByIdResultat(Long idResultat) throws ResultatNotFoundException, PersonneNotFoundException, ProduitNotFoundException, PrelevementNotFoundException;
}
