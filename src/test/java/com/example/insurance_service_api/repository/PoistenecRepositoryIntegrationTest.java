package com.example.insurance_service_api.repository;

import com.example.insurance_service_api.model.Adresa;
import com.example.insurance_service_api.model.Poistenec;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PoistenecRepositoryIntegrationTest {

    @Autowired
    private PoistenecRepository poistenecRepository;

    @Test
    void GIVEN_savedPoistenec_WHEN_existsByRodneCislo_THEN_returnTrue() {
        // Given
        Poistenec poistenec = createPoistenec("9001011234", "test@example.com");
        poistenecRepository.save(poistenec);

        // When
        boolean exists = poistenecRepository.existsByRodneCislo("9001011234");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void GIVEN_savedPoistenec_WHEN_existsByEmail_THEN_returnTrue() {
        // Given
        Poistenec poistenec = createPoistenec("9101011234", "unique@example.com");
        poistenecRepository.save(poistenec);

        // When
        boolean exists = poistenecRepository.existsByEmail("unique@example.com");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void GIVEN_nothingSaved_WHEN_existsByRodneCislo_THEN_returnFalse() {
        boolean exists = poistenecRepository.existsByRodneCislo("0000000000");
        assertThat(exists).isFalse();
    }

    private Poistenec createPoistenec(String rodneCislo, String email) {
        return Poistenec.builder()
                .meno("Peter")
                .priezvisko("Mrkvička")
                .rodneCislo(rodneCislo)
                .email(email)
                .adresaTrvalehoPobytu(createAdresa())
                .build();
    }

    private Adresa createAdresa() {
        return Adresa.builder()
                .ulica("Hlavná")
                .cisloDomu("12")
                .obec("Bratislava")
                .psc("12345")
                .build();
    }
}