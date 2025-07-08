package com.insurance_service_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdresaDto {

    @NotBlank(message = "PSC je povinny udaj.")
    private String psc;

    @NotBlank(message = "Obec je povinny udaj.")
    private String obec;

    @NotBlank(message = "Ulica je povinny udaj.")
    private String ulica;

    @NotBlank(message = "Číslo domu je povinny udaj.")
    private String cisloDomu;
}
