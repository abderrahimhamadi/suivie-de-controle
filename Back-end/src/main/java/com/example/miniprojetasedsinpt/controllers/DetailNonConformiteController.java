package com.example.miniprojetasedsinpt.controllers;

import com.example.miniprojetasedsinpt.dtos.DetailNonConformiteDTO;
import com.example.miniprojetasedsinpt.entities.DetailNonConformite;
import com.example.miniprojetasedsinpt.exceptions.*;
import com.example.miniprojetasedsinpt.mappers.DetailMapper;
import com.example.miniprojetasedsinpt.repositories.DetailNonConformiteRepository;
import com.example.miniprojetasedsinpt.services.DetailNonConformiteService;
import com.example.miniprojetasedsinpt.services.ResultatPrelevementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("detailNonConformite")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@Slf4j
public class DetailNonConformiteController {
    private final DetailNonConformiteService detailNonConformiteService;
    private final ResultatPrelevementService resultatPrelevementService;
    private final DetailNonConformiteRepository detailNonConformiteRepository;
    private final DetailMapper detailMapper;
    @GetMapping
    public List<DetailNonConformiteDTO> getAllDetails() {
        return detailNonConformiteService.getAllDetails();
    }

    @DeleteMapping("/resultat/{idResultat}")
    public void deleteDetail(@PathVariable Long idResultat) {
        DetailNonConformite detailNonConformite = detailNonConformiteRepository.findByResultatPrelId(idResultat);
        detailNonConformiteRepository.delete(detailNonConformite);
    }

    @GetMapping("/{id}")
    public DetailNonConformiteDTO getDetail(@PathVariable Long id) throws DetailNotFoundException {
        return 	detailNonConformiteService.getDetail(id);
    }

    @GetMapping("/resultat/{idResultat}")
    public DetailNonConformiteDTO getDetailByResultat(@PathVariable Long idResultat)
            throws ResultatNotFoundException, PersonneNotFoundException,
            ProduitNotFoundException, PrelevementNotFoundException
    {
        return detailNonConformiteService.getDetailByIdResultat(idResultat);
    }

    @PostMapping
    public DetailNonConformiteDTO saveDetail(@RequestBody DetailNonConformiteDTO detailNonConformiteDTO)
            throws ResultatNotFoundException, PersonneNotFoundException,
            ProduitNotFoundException, PrelevementNotFoundException
    {
        log.info("Hello from saving detail");
        return detailNonConformiteService.saveDetail(detailNonConformiteDTO);
    }

}
