package com.example.miniprojetasedsinpt.services;

import com.example.miniprojetasedsinpt.dtos.PersonneDTO;
import com.example.miniprojetasedsinpt.entities.Personne;
import com.example.miniprojetasedsinpt.exceptions.PersonneNotFoundException;

import java.util.List;

public interface PersonneService {
    PersonneDTO savePersonne(PersonneDTO personneDTO);
    PersonneDTO getPersonne(Long id) throws PersonneNotFoundException;
    List<PersonneDTO> getAllPersonnes();
}
