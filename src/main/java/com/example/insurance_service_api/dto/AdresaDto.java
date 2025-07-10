package com.example.insurance_service_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdresaDto {

    @NotBlank(message = "PSC je povinny udaj.")
    @Pattern(regexp = "^\\d{5}$", message = "PSC musi obsahovai 5 cislic.")
    private String psc;

    @NotBlank(message = "Obec je povinny udaj.")
    @Pattern(regexp = "^[A-Za-zÀ-ž\\s'-]+$", message = "Obec moze obsahovat iba pismena a medzery.")
    private String obec;

    @NotBlank(message = "Ulica je povinny udaj.")
    private String ulica;

    @NotBlank(message = "Císlo domu je povinny udaj.")
    @Pattern(regexp = "^\\d+[A-Za-z]?$", message = "Císlo domu musi byt cislo, pripadne s pismenom (napr. 15, 23A).")
    private String cisloDomu;
}
