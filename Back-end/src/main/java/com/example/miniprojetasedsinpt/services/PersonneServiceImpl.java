package com.example.miniprojetasedsinpt.services;

import com.example.miniprojetasedsinpt.dtos.PersonneDTO;
import com.example.miniprojetasedsinpt.entities.Personne;
import com.example.miniprojetasedsinpt.exceptions.PersonneNotFoundException;
import com.example.miniprojetasedsinpt.mappers.PersonneMapper;
import com.example.miniprojetasedsinpt.repositories.PersonneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonneServiceImpl implements PersonneService{
    private final PersonneRepository personneRepository;
    private final PersonneMapper personneMapper;


    @Override
    public PersonneDTO savePersonne(PersonneDTO personneDTO) {
        Personne personne = personneMapper.fromPersonneDTO(personneDTO);
        Personne savedPersonne = personneRepository.save(personne);
        return personneMapper.fromPersonne(savedPersonne);
    }

    @Override
    public PersonneDTO getPersonne(Long id) throws PersonneNotFoundException {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new PersonneNotFoundException("Ce personne n'existe pas"));
        return personneMapper.fromPersonne(personne);
    }

    @Override
    public List<PersonneDTO> getAllPersonnes() {
        List<Personne> personnes = personneRepository.findAll();
        List<PersonneDTO> personneDTOS = personnes.stream()
                .map(personne -> personneMapper.fromPersonne(personne))
                .collect(Collectors.toList());
        return personneDTOS;
    }
}
