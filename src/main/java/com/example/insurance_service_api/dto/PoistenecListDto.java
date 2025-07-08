package com.example.insurance_service_api.dto;

import lombok.Data;

@Data
public class PoistenecListDto {
    private Long id;
    private String meno;
    private String priezvisko;
    private String rodneCislo;
    private String email;
}
