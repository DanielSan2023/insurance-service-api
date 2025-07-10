package com.example.insurance_service_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("CestovnePoistenie")
public class CestovnePoistenie extends Zmluva {

    @Column(nullable = false)
    private LocalDate datumZaciatku;

    @Column(nullable = false)
    private LocalDate datumKonca;

    @Column(nullable = false)
    private Boolean poistenieZodpovednosti = false;

    @Column(nullable = false)
    private Boolean poistenieUrazu =false;
}