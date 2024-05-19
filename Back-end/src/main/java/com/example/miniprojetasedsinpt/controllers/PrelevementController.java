package com.example.miniprojetasedsinpt.controllers;

import com.example.miniprojetasedsinpt.dtos.PrelevementDTO;
import com.example.miniprojetasedsinpt.dtos.PrelevementResponseDTO;
import com.example.miniprojetasedsinpt.dtos.ProduitDTO;
import com.example.miniprojetasedsinpt.entities.utils.EtatAvancement;
import com.example.miniprojetasedsinpt.entities.utils.Labo;
import com.example.miniprojetasedsinpt.exceptions.NomOrCategorieIsNullException;
import com.example.miniprojetasedsinpt.exceptions.PersonneNotFoundException;
import com.example.miniprojetasedsinpt.exceptions.PrelevementNotFoundException;
import com.example.miniprojetasedsinpt.exceptions.ProduitNotFoundException;
import com.example.miniprojetasedsinpt.security.JwtService;
import com.example.miniprojetasedsinpt.services.PrelevementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/prelevement")
@Slf4j
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class PrelevementController {
    private final PrelevementService prelevementSrevice;
    private final JwtService jwtService;

    @GetMapping()

    public ResponseEntity<PrelevementResponseDTO> getAllPrelevement(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "etat", defaultValue = "") EtatAvancement etatAvancement
            ) {
        PrelevementResponseDTO allPrelevement =
                prelevementSrevice.getAllPrelevement(keyword, etatAvancement, page, size);
        return new ResponseEntity<>(allPrelevement, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public PrelevementDTO getPrelevement(@PathVariable Long id)
            throws PrelevementNotFoundException {
        return prelevementSrevice.getPrelevement(id);

    }

    @PostMapping
    //@PreAuthorize("hasAuthority('AGENT_CONTROLE')")
    public PrelevementDTO savePrelevement(
            HttpServletRequest request,
            @RequestBody PrelevementDTO prelevementDTO)
            throws PersonneNotFoundException, ProduitNotFoundException, NomOrCategorieIsNullException {
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        Long idPersonne = jwtService.extractIdPersonne(jwt);
        return prelevementSrevice.savePrelevement(prelevementDTO);
    }

    @GetMapping("/personne")
    public PrelevementResponseDTO getAllPrelevementByPersonne(
            HttpServletRequest request,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "etat", defaultValue = "") EtatAvancement etatAvancement,
            @RequestParam(name = "numeroProcesVerbal", defaultValue = "") String numeroProcesVerbal
    ) throws PersonneNotFoundException
    {
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        Long idPersonne = jwtService.extractIdPersonne(jwt);
        return prelevementSrevice.getAllPrelevementByPersonne(idPersonne, keyword, etatAvancement, numeroProcesVerbal, page, size);
    }

    @GetMapping("/labo")
    public PrelevementResponseDTO getAllPrelevementByLabo(
            HttpServletRequest request,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "etat", defaultValue = "") EtatAvancement etatAvancement,
            @RequestParam(name = "numeroProcesVerbal", defaultValue = "") String numeroProcesVerbal
    ) throws PersonneNotFoundException
    {
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String laboString = jwtService.extractLabo(jwt);
        log.info(laboString);
        Labo labo;
        if(laboString.equals("LOARC")){
            labo = Labo.LOARC;
            log.info(labo.name());
        }else {
            labo = Labo.LRAR;
            log.info(labo.name());
        }
        return prelevementSrevice.getAllPrelevementByLabo(labo, keyword, etatAvancement, numeroProcesVerbal, page, size);
    }

    @DeleteMapping("{id}")
    //@PreAuthorize("hasAuthority('AGENT_CONTROLE')")
    public void deletePrelevement(@PathVariable int id) throws PrelevementNotFoundException {
        prelevementSrevice.deletePrelevement(id);
    }


}
