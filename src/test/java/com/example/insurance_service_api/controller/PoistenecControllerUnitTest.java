package com.example.insurance_service_api.controller;


import com.example.insurance_service_api.dto.*;
import com.example.insurance_service_api.dto.AdresaDto;
import com.example.insurance_service_api.dto.PoistenecCreateDto;
import com.example.insurance_service_api.dto.PoistenecListDto;
import com.example.insurance_service_api.service.ServiceImpl.PoistenecService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PoistenecControllerUnitTest {

    @Mock
    private PoistenecService poistenecService;

    @InjectMocks
    private PoistenecController poistenecController;

    @Test
    void GIVEN_empty_poistenci_list_WHEN_getAllPoistenci_THEN_return_Status_404() {
        //GIVEN
        when(poistenecService.getAllPoistenci()).thenReturn(Collections.emptyList());

        //WHEN
        ResponseEntity<List<PoistenecListDto>> response = poistenecController.getAllPoistenci();

        //THEN
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void GIVEN_poistenci_list_WHEN_getAllPoistenci_THEN_return_Ok_Status_poistenci_list() {
        //GIVEN
        PoistenecListDto dto = new PoistenecListDto();
        dto.setId(1L);
        dto.setMeno("Jozef");
        dto.setPriezvisko("Kral");
        dto.setRodneCislo("9102031234");
        dto.setEmail("jozef@example.com");

        when(poistenecService.getAllPoistenci()).thenReturn(List.of(dto));

        //WHEN
        ResponseEntity<List<PoistenecListDto>> response = poistenecController.getAllPoistenci();

        //THEN
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getMeno()).isEqualTo("Jozef");
    }

    @Test
    void GIVEN_valid_poistenec_createDto_WHEN_createPoistenec_THEN_return_unique_number() {
        //GIVEN
        PoistenecCreateDto createDto = PoistenecCreateDto.builder()
                .meno("Anna")
                .priezvisko("Kovacova")
                .rodneCislo("9001011234")
                .email("anna@example.com")
                .adresaTrvalehoPobytu(createAdresaDto())
                .build();

        when(poistenecService.createPoistenec(any(PoistenecCreateDto.class)))
                .thenReturn(new PoistenecDtoIdentifikator(99L));

        //WHEN
        ResponseEntity<Long> response = poistenecController.createPoistenec(createDto);

        //THEN
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(99L);
    }

    @Test
    void GIVEN_existing_id_WHEN_getPoistenecById_THEN_return_full_dto() {
        //GIVEN
        PoistenecFullDto dto = new PoistenecFullDto();
        dto.setId(1L);
        dto.setMeno("Peter");
        dto.setPriezvisko("Urban");
        dto.setRodneCislo("8501011234");
        dto.setEmail("peter@example.com");

        when(poistenecService.getPoistenecById(1L)).thenReturn(dto);

        //WHEN
        ResponseEntity<PoistenecFullDto> response = poistenecController.getPoistenecById(1L);

        //THEN
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getEmail()).isEqualTo("peter@example.com");
    }


    private @Valid @NotNull(message = "Adresa poistenca je povinny udaj.") AdresaDto createAdresaDto() {
        AdresaDto adresa = new AdresaDto();
        adresa.setPsc("12345");
        adresa.setObec("Bratislava");
        adresa.setUlica("Hlavna");
        adresa.setCisloDomu("12");
        return adresa;
    }
}
