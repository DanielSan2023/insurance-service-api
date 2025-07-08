package com.insurance_service_api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class ZmluvaDTO {
    private Long id;
    private String cisloZmluvy;
    private LocalDate datumVzniku;
    private String typZmluvy;
}
