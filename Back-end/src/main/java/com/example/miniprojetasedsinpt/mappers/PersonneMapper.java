package com.example.miniprojetasedsinpt.mappers;

import com.example.miniprojetasedsinpt.dtos.PersonneDTO;
import com.example.miniprojetasedsinpt.entities.Personne;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonneMapper {
    private final PasswordEncoder passwordEncoder;

    public PersonneDTO fromPersonne(Personne personne) {
        PersonneDTO personneDTO = new PersonneDTO();
        BeanUtils.copyProperties(personne,personneDTO);
        return personneDTO;
    }

    public Personne fromPersonneDTO(PersonneDTO personneDTO) {
        Personne personne= new Personne();
        BeanUtils.copyProperties(personneDTO,personne);
        personne.setMdp(passwordEncoder.encode(personneDTO.getMdp()));
        return personne;
    }

}
