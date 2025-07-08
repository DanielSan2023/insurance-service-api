package com.example.insurance_service_api.dto;

import com.example.insurance_service_api.model.TypNehnutelnosti;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class PoistenieMajetkuDto extends ZmluvaDTO {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Typ nehnuteľnosti je povinný")
    private TypNehnutelnosti typNehnutelnosti;
    private AdresaDto adresaNehnutelnosti;
    private BigDecimal hodnotaNehnutelnosti;
}