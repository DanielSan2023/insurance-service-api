package com.insurance_service_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PoistenecCreateDto {

    @NotBlank(message = "Meno je povinny udaj.")
    private String meno;

    @NotBlank(message = "Priezvisko je povinny udaj.")
    private String priezvisko;

    @NotBlank(message = "Rodne cislo je povinny udaj.")
    private String rodneCislo;

    @NotBlank(message = "Emailova adresa je povinny udaj.")
    @Email
    private String email;

    @Valid
    @NotNull(message = "Adresa poistenca je povinny udaj.")
    private AdresaDto adresaTrvalehoPobytu;

    @Valid
    private AdresaDto korespondencnaAdresa;
}
