package com.example.insurance_service_api;

import com.example.insurance_service_api.dto.AdresaDto;
import com.example.insurance_service_api.dto.PoistenecCreateDto;
import com.example.insurance_service_api.model.Poistenec;
import com.example.insurance_service_api.repository.CestovnePoistenieRepository;
import com.example.insurance_service_api.repository.PoistenecRepository;
import com.example.insurance_service_api.repository.PoistenieMajetkuRepository;
import com.example.insurance_service_api.repository.ZmluvaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PoistenecIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PoistenecRepository poistenecRepository;

    @Autowired
    private ZmluvaRepository  zmluvaRepository;

    @Autowired
    private CestovnePoistenieRepository cestovnePoistenieRepository;

    @Autowired
    private PoistenieMajetkuRepository poistenieMajetkuRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        zmluvaRepository.deleteAll();
        cestovnePoistenieRepository.deleteAll();
        poistenieMajetkuRepository.deleteAll();
        poistenecRepository.deleteAll();
    }

    @Test
    void shouldCreateAndGetPoistenecSuccessfully() throws Exception {
        // GIVEN
        PoistenecCreateDto dto = new PoistenecCreateDto();
        dto.setMeno("Peter");
        dto.setPriezvisko("Novák");
        dto.setRodneCislo("9001011234");
        dto.setEmail("peter.novak@example.com");

        AdresaDto adresa = new AdresaDto();
        adresa.setUlica("Hlavná 1");
        adresa.setObec("Bratislava");
        adresa.setPsc("81101");
        adresa.setCisloDomu("81");
        dto.setAdresaTrvalehoPobytu(adresa);
        dto.setKorespondencnaAdresa(null);

        String response = mockMvc.perform(post("/insured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = Long.parseLong(response);
        //WHEN & THEN
        mockMvc.perform(get("/insured/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meno").value("Peter"))
                .andExpect(jsonPath("$.priezvisko").value("Novák"))
                .andExpect(jsonPath("$.rodneCislo").value("9001011234"))
                .andExpect(jsonPath("$.email").value("peter.novak@example.com"))
                .andExpect(jsonPath("$.korespondencnaAdresa.ulica").value("Hlavná 1")); // kontrola automatického kopírovania adresy

        assertThat(poistenecRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldReturn400_WhenDuplicateRodneCisloOrEmail() throws Exception {
        // GIVEN
        Poistenec poistenec = new Poistenec();
        poistenec.setMeno("Jan");
        poistenec.setPriezvisko("Mrkvička");
        poistenec.setRodneCislo("9001011234");
        poistenec.setEmail("jan@example.com");
        poistenec.setAdresaTrvalehoPobytu(modelMapper.map(new AdresaDto("Hlavná", "Bratislava", "81101","15"), com.example.insurance_service_api.model.Adresa.class));
        poistenecRepository.save(poistenec);

        PoistenecCreateDto dto = new PoistenecCreateDto();
        dto.setMeno("Kópia");
        dto.setPriezvisko("Záznam");
        dto.setRodneCislo("9001011234"); // duplicate
        dto.setEmail("jan@example.com"); // duplicate
        dto.setAdresaTrvalehoPobytu(new AdresaDto("Iná", "BA", "90001","10"));

        // WHEN & THEN
        mockMvc.perform(post("/insured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
