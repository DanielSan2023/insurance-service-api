package com.example.insurance_service_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ZmluvaDTO {
    private Long id;
    private String cisloZmluvy;
    private LocalDate datumVzniku;
    private String typZmluvy;
}
