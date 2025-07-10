package com.example.insurance_service_api.controller;

import com.example.insurance_service_api.dto.AdresaDto;
import com.example.insurance_service_api.dto.PoistenecCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PoistenecControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void GIVEN_invalid_meno_WHEN_createPoistenec_THEN_return_400_and_error_message() throws Exception {
        PoistenecCreateDto invalidDto = PoistenecCreateDto.builder()
                .meno("12345")
                .priezvisko("Novak")
                .rodneCislo("9001011234")
                .email("test@example.com")
                .adresaTrvalehoPobytu(createAdresaDto())
                .build();

        mockMvc.perform(post("/insured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad request"))
                .andExpect(jsonPath("$.message").value(containsString("Meno moze obsahovat iba pismena a medzery.")));
    }

    @Test
    void GIVEN_invalid_email_WHEN_createPoistenec_THEN_return_400() throws Exception {
        PoistenecCreateDto invalidDto = PoistenecCreateDto.builder()
                .meno("Jana")
                .priezvisko("Kovacova")
                .rodneCislo("9102031234")
                .email("not-an-email")
                .adresaTrvalehoPobytu(createAdresaDto())
                .build();

        mockMvc.perform(post("/insured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad request"))
                .andExpect(jsonPath("$.message").value(containsString("Zadany email nema spravny format")));
    }

    @Test
    void GIVEN_null_trvala_adresa_WHEN_createPoistenec_THEN_return_400() throws Exception {
        PoistenecCreateDto invalidDto = PoistenecCreateDto.builder()
                .meno("Peter")
                .priezvisko("Mrkvicka")
                .rodneCislo("8501011234")
                .email("peter@example.com")
                .adresaTrvalehoPobytu(null)
                .build();

        mockMvc.perform(post("/insured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad request"))
                .andExpect(jsonPath("$.message").value(containsString("Adresa poistenca je povinny udaj")));
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
