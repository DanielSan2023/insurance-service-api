package com.example.insurance_service_api.dto;

import com.example.insurance_service_api.model.Zmluva;
import lombok.Data;

import java.util.List;

@Data
public class PoistenecFullDto {
    private Long id;
    private String meno;
    private String priezvisko;
    private String rodneCislo;
    private String email;
    private AdresaDto adresaTrvalehoPobytu;

    private AdresaDto korespondencnaAdresa;
    private List<Zmluva> zmluvy;
}
