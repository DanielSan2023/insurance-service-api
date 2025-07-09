package com.example.insurance_service_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[A-Za-zÀ-ž\\s'-]+$", message = "Meno moze obsahovat iba pismena a medzery.")
    private String meno;

    @NotBlank(message = "Priezvisko je povinny udaj.")
    @Pattern(regexp = "^[A-Za-zÀ-ž\\s'-]+$", message = "Priezvisko moze obsahovat iba pismena a medzery.")
    private String priezvisko;

    @NotBlank(message = "Rodne cislo je povinny udaj.")
    @Pattern(regexp = "^\\d{6}/?\\d{3,4}$", message = "Rodne císlo musí byt vo formate 6 cislic + 3 alebo 4 cislice, napr. 9102031234 alebo 910203/1234.")
    private String rodneCislo;

    @NotBlank(message = "Emailova adresa je povinny udaj.")
    @Email(message = "Zadany email nema spravny format.")
    private String email;

    @Valid
    @NotNull(message = "Adresa poistenca je povinny udaj.")
    private AdresaDto adresaTrvalehoPobytu;

    @Valid
    private AdresaDto korespondencnaAdresa;
}
