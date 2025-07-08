package com.example.insurance_service_api.model;

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
@DiscriminatorValue("CESTOVNE")
public class CestovnePoistenie extends Zmluva {
    private LocalDate datumZaciatku;
    private LocalDate datumKonca;
    private boolean poistenieZodpovednosti;
    private boolean poistenieUrazu;
}