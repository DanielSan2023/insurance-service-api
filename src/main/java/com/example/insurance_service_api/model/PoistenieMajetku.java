package com.example.insurance_service_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("PoistenieMajetku")
public class PoistenieMajetku extends Zmluva {

    @Column(nullable = false)
    private LocalDate datumKonca;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypNehnutelnosti typNehnutelnosti;

    @Embedded
    @Column(nullable = false)
    private Adresa adresaNehnutelnosti;

    @Column(nullable = false)
    private BigDecimal hodnotaNehnutelnosti;
}