package com.example.insurance_service_api.validator;

import com.example.insurance_service_api.model.Adresa;
import com.example.insurance_service_api.model.Poistenec;
import com.example.insurance_service_api.repository.PoistenecRepository;
import com.example.insurance_service_api.utility.PoistenecConstants;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class PoistenecValidatorTest {

    private PoistenecRepository poistenecRepository;
    private PoistenecValidator validator;

    @BeforeEach
    void setUp() {
        poistenecRepository = mock(PoistenecRepository.class);
        validator = new PoistenecValidator(poistenecRepository);
    }

    @Test
    void GIVEN_duplicate_rodne_cislo_WHEN_checkForDuplicateRodneCislo_THEN_throw_exception() {
        // Given
        String rodneCislo = "9001011234";
        when(poistenecRepository.existsByRodneCislo(rodneCislo)).thenReturn(true);

        String expectedMessage = String.format(PoistenecConstants.DUPLICATE_RODNE_CISLO_EXCEPTION,rodneCislo);
        // Then
        assertThatThrownBy(() -> validator.checkForDuplicateRodneCislo(rodneCislo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    void GIVEN_unique_rodne_cislo_WHEN_checkForDuplicateRodneCislo_THEN_do_nothing() {
        when(poistenecRepository.existsByRodneCislo("9001011234")).thenReturn(false);

        validator.checkForDuplicateRodneCislo("9001011234");

        verify(poistenecRepository).existsByRodneCislo("9001011234");
    }

    @Test
    void GIVEN_duplicate_email_WHEN_checkForDuplicateEmail_THEN_throw_exception() {
        String email = "test@example.com";
        when(poistenecRepository.existsByEmail(email)).thenReturn(true);
        String expectedMessage = String.format(PoistenecConstants.DUPLICATE_EMAIL_ADDRESS_EXCEPTION, email);

        assertThatThrownBy(() -> validator.checkForDuplicateEmail(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    void GIVEN_unique_email_WHEN_checkForDuplicateEmail_THEN_do_nothing() {
        when(poistenecRepository.existsByEmail("test@example.com")).thenReturn(false);

        validator.checkForDuplicateEmail("test@example.com");

        verify(poistenecRepository).existsByEmail("test@example.com");
    }

    @Test
    void GIVEN_null_korespondencna_adresa_WHEN_checkIfKorespondencnaAdresaIsNull_THEN_set_to_trvala_adresa() {
        Poistenec poistenec = new Poistenec();
        Adresa trvalaAdresa = new Adresa();
        poistenec.setAdresaTrvalehoPobytu(trvalaAdresa);
        poistenec.setKorespondencnaAdresa(null);

        validator.checkIfKorespondencnaAdresaIsNull(poistenec);

        assertThat(poistenec.getKorespondencnaAdresa()).isEqualTo(trvalaAdresa);
    }

    @Test
    void GIVEN_non_null_korespondencna_adresa_WHEN_checkIfKorespondencnaAdresaIsNull_THEN_do_nothing() {
        Poistenec poistenec = new Poistenec();
        Adresa trvalaAdresa = new Adresa();
        Adresa korespondencna = new Adresa();
        poistenec.setAdresaTrvalehoPobytu(trvalaAdresa);
        poistenec.setKorespondencnaAdresa(korespondencna);

        validator.checkIfKorespondencnaAdresaIsNull(poistenec);

        assertThat(poistenec.getKorespondencnaAdresa()).isEqualTo(korespondencna);
    }

    @Test
    void GIVEN_empty_poistenci_WHEN_validatePoistenciExist_THEN_throw_EntityNotFoundException() {
        List<Poistenec> emptyList = Collections.emptyList();

        assertThatThrownBy(() -> validator.validatePoistenciExist(emptyList))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(PoistenecConstants.POISTENCI_NOT_FOUND);
    }

    @Test
    void GIVEN_non_empty_poistenci_WHEN_validatePoistenciExist_THEN_do_nothing() {
        List<Poistenec> list = List.of(new Poistenec());

        validator.validatePoistenciExist(list);
    }
}