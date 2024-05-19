package com.example.miniprojetasedsinpt.mappers;

import com.example.miniprojetasedsinpt.dtos.DetailNonConformiteDTO;
import com.example.miniprojetasedsinpt.entities.DetailNonConformite;
import com.example.miniprojetasedsinpt.entities.ResultatPrelevement;
import com.example.miniprojetasedsinpt.exceptions.PersonneNotFoundException;
import com.example.miniprojetasedsinpt.exceptions.PrelevementNotFoundException;
import com.example.miniprojetasedsinpt.exceptions.ProduitNotFoundException;
import com.example.miniprojetasedsinpt.exceptions.ResultatNotFoundException;
import com.example.miniprojetasedsinpt.services.ResultatPrelevementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetailMapper {
    private final ResultatPrelevementService resultatPrelevementService;
    private final ResultatPrelevementMapper resultatPrelevementMapper;

    public DetailNonConformiteDTO fromDetailNonConformite(DetailNonConformite detailNonConformite) {
        DetailNonConformiteDTO detailNonConformiteDTO = new DetailNonConformiteDTO();
        BeanUtils.copyProperties(detailNonConformite, detailNonConformiteDTO);
        detailNonConformiteDTO.setIdResultat(detailNonConformite.getResultatPrel().getId());
        return detailNonConformiteDTO;
    }

    public DetailNonConformite fromDetailNonConformiteDTO(DetailNonConformiteDTO detailNonConformiteDTO)
            throws ResultatNotFoundException, PersonneNotFoundException, ProduitNotFoundException, PrelevementNotFoundException
    {
        DetailNonConformite detailNonConformite = new DetailNonConformite();
        BeanUtils.copyProperties(detailNonConformiteDTO, detailNonConformite);
        ResultatPrelevement resultatPrelevement = resultatPrelevementMapper
                .fromResultatPrelevementDTO(resultatPrelevementService
                        .getResultatPrelevement(detailNonConformiteDTO.getIdResultat()));
        detailNonConformite.setResultatPrel(resultatPrelevement);
        return  detailNonConformite;
    }

}
