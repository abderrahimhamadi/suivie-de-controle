package com.example.miniprojetasedsinpt.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

;

@Entity
@Data
@NoArgsConstructor
public class DetailNonConformite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dateTA;
    private String numeroTA;
    private String detail;
    @OneToOne
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ResultatPrelevement resultatPrel;
}
