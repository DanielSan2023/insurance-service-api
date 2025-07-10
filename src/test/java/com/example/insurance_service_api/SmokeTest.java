package com.example.insurance_service_api;

import com.example.insurance_service_api.model.*;
import com.example.insurance_service_api.repository.CestovnePoistenieRepository;
import com.example.insurance_service_api.repository.PoistenecRepository;
import com.example.insurance_service_api.repository.PoistenieMajetkuRepository;
import com.example.insurance_service_api.repository.ZmluvaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {InsuranceServiceApiApplication.class})
@AutoConfigureMockMvc
public class SmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PoistenecRepository poistenecRepository;

    @Autowired
    private ZmluvaRepository zmluvaRepository;

    @Autowired
    private CestovnePoistenieRepository cestovnePoistenieRepository;

    @Autowired
    private PoistenieMajetkuRepository poistenieMajetkuRepository;

    private Long poistenecId;

    @BeforeEach
    void cleanDatabase() {
        zmluvaRepository.deleteAll();
        cestovnePoistenieRepository.deleteAll();
        poistenieMajetkuRepository.deleteAll();
        poistenecRepository.deleteAll();
    }

    @BeforeEach
    void setupData() {
        Adresa adresa = new Adresa();
        adresa.setUlica("Hlavná 1");
        adresa.setObec("Bratislava");
        adresa.setPsc("81101");
        adresa.setCisloDomu("81");

        Poistenec p1 = new Poistenec();
        p1.setMeno("Jozef");
        p1.setPriezvisko("Novak");
        p1.setEmail("jozef.novak@example.com");
        p1.setRodneCislo("9001011234");
        p1.setAdresaTrvalehoPobytu(adresa);
        p1.setKorespondencnaAdresa(adresa);

        CestovnePoistenie cp = new CestovnePoistenie();
        cp.setCisloZmluvy("ZML-CP-123456");
        cp.setDatumVzniku(LocalDate.now());
        cp.setDatumZaciatku(LocalDate.now());
        cp.setDatumKonca(LocalDate.now().plusDays(10));
        cp.setPoistenieUrazu(true);
        cp.setPoistenieZodpovednosti(false);
        cp.setPoistenec(p1);

        PoistenieMajetku mp = new PoistenieMajetku();
        mp.setDatumVzniku(LocalDate.now());
        mp.setDatumKonca(LocalDate.now().plusMonths(12));
        mp.setCisloZmluvy("ZML-PM-654321");
        mp.setPoistenec(p1);

        p1.getZmluvy().add(cp);
        p1.getZmluvy().add(mp);

        Poistenec saved = poistenecRepository.save(p1);
        poistenecId = saved.getId();

        zmluvaRepository.save(cp);
        zmluvaRepository.save(mp);
    }

    @Test
    void GIVEN_poistenec_and_zmluva_WHEN_getPoistenci_THEN_return_correct_count() throws Exception {
        mockMvc.perform(get("/insured")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].meno").value("Jozef"))
                .andExpect(jsonPath("$[0].priezvisko").value("Novak"));
    }

    @Test
    void GIVEN_zmluva_WHEN_getZmluvy_THEN_return_correct_count() throws Exception {
        mockMvc.perform(get("/insured/{id}", poistenecId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zmluvy.length()").value(2))
                .andExpect(jsonPath("$.zmluvy[?(@.poistenieUrazu == true && @.poistenieZodpovednosti == false)]").exists())
                .andExpect(jsonPath("$.zmluvy[?(@.cisloZmluvy == 'ZML-PM-654321')]").exists())
                .andExpect(jsonPath("$.zmluvy[?(@.cisloZmluvy == 'ZML-CP-123456')]").exists());
    }
}
