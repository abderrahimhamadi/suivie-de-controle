package com.example.miniprojetasedsinpt.controllers;

import com.example.miniprojetasedsinpt.dtos.ResultatPrelevementDTO;
import com.example.miniprojetasedsinpt.dtos.ResultatPrelevementResponseDTO;
import com.example.miniprojetasedsinpt.exceptions.*;
import com.example.miniprojetasedsinpt.services.PersonneService;
import com.example.miniprojetasedsinpt.services.PrelevementService;
import com.example.miniprojetasedsinpt.services.ResultatPrelevementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resultatPrelevement")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class ResultatPrelevementController {
    private final ResultatPrelevementService resultatPrelevementService;
    private final PersonneService personneService;
    private final PrelevementService prelevementService;

    @GetMapping
    public ResultatPrelevementResponseDTO getAllPrelevement(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        return resultatPrelevementService.getAllResultatPrelevement(page, size);
    }

    @GetMapping("/personne/{idPersonne}")
    public ResultatPrelevementResponseDTO getAllResultatPrelevementByPersonne(
            @PathVariable Long idPersonne,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "keyword", defaultValue = "") String kw,
            @RequestParam(name = "numeroBA", defaultValue = "") String numeroBA,
            @RequestParam(name = "conforme", defaultValue = "") String conforme
    ) throws PersonneNotFoundException {
        return resultatPrelevementService.getAllResultatPrelevementByPersonne(idPersonne,kw, numeroBA, conforme, page, size);
    }

    @GetMapping("/personne/prelevement/{idPersonne}")
    public ResultatPrelevementResponseDTO getAllResultatPrelevementByPersonneAndPrelevement(
            @PathVariable Long idPersonne,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "numeroBA", defaultValue = "") String numeroBA,
            @RequestParam(name = "conforme", defaultValue = "") String conforme
    ) throws PersonneNotFoundException
    {
        return resultatPrelevementService.getAllResultatPrelevementByPersonneAndPrelevement(idPersonne, keyword, numeroBA, conforme, page, size);
    }

    @GetMapping("/{id}")
    public ResultatPrelevementDTO getResultatPrelevement(@PathVariable Long id)
            throws ResultatNotFoundException
    {
        return resultatPrelevementService.getResultatPrelevement(id);
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('RESPO_LABO')")
    public ResultatPrelevementDTO saveResultatPrelevement(
            @RequestBody ResultatPrelevementDTO resultatPrelevementDTO
    ) throws PersonneNotFoundException, ProduitNotFoundException, PrelevementNotFoundException, NomOrCategorieIsNullException {
        return resultatPrelevementService.saveResultatPrelevement(resultatPrelevementDTO);
    }

    @DeleteMapping("{id}")
    //@PreAuthorize("hasAuthority('RESPO_LABO')")
    public void deleteResultatPrelevement(@PathVariable Long id) throws ResultatNotFoundException {
        resultatPrelevementService.deleteResultatPrelevement(id);
    }

}
