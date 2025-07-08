package com.insurance_service_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("MAJETOK")
public class PoistenieMajetku extends Zmluva {

    @Enumerated(EnumType.STRING)
    @NotNull
    private TypNehnutelnosti typNehnutelnosti;

    @Embedded
    @NotNull
    private Adresa adresaNehnutelnosti;

    @NotNull
    private BigDecimal hodnotaNehnutelnosti;
}