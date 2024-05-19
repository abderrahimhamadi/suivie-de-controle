package com.example.miniprojetasedsinpt.security;

import com.example.miniprojetasedsinpt.dtos.PersonneDTO;
import com.example.miniprojetasedsinpt.entities.Personne;
import com.example.miniprojetasedsinpt.entities.utils.TypePersonne;
import com.example.miniprojetasedsinpt.mappers.PersonneMapper;
import com.example.miniprojetasedsinpt.repositories.PersonneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final PersonneMapper personneMapper;
    private final PersonneRepository personneRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(PersonneDTO personneDTO){
        String email = personneDTO.getEmail();
        Optional<Personne> existingPersonne = personneRepository.findByEmail(email);
        if(existingPersonne.isPresent() ) {
            String errorMessage = "ce compte avec cet email existe déjà";
            return AuthenticationResponse.builder()
                    .token(null) // Set token to null since it's an error response
                    .errorMessage(errorMessage)
                    .build();
        }
        Personne personne = personneMapper.fromPersonneDTO(personneDTO);
        Personne savedPersonne = personneRepository.save(personne);
        UserDetails userDetails = new UserRegistrationDetails(savedPersonne);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("idPersonne", savedPersonne.getId());
        extraClaims.put("type", savedPersonne.getType());
        extraClaims.put("nom", savedPersonne.getPrenom());
        if(personneDTO.getType().equals(TypePersonne.RESPO_LABO)){
            extraClaims.put("labo", savedPersonne.getLabo());
        }
        String jwtToken = jwtService.generateToken(extraClaims,userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Personne personne = personneRepository.findByEmail(request.getEmail()).orElseThrow();
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("idPersonne", personne.getId());
        extraClaims.put("type", personne.getType());
        extraClaims.put("nom", personne.getPrenom());
        if(personne.getType().equals(TypePersonne.RESPO_LABO)){
            extraClaims.put("labo", personne.getLabo());
        }
        UserDetails userDetails = new UserRegistrationDetails(personne);
        String jwtToken = jwtService.generateToken(extraClaims, userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


}