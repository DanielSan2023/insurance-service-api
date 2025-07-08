package com.insurance_service_api.dto;

import com.insurance_service_api.model.TypNehnutelnosti;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class PoistenieMajetkuDto extends ZmluvaDTO {

    @Enumerated(EnumType.STRING)
    private TypNehnutelnosti typNehnutelnosti;
    private AdresaDto adresaNehnutelnosti;
    private BigDecimal hodnotaNehnutelnosti;
}