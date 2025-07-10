package com.example.insurance_service_api.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PoistenecRepositoryTest {

    @Mock
    private PoistenecRepository poistenecRepository;

    @Test
    void GIVEN_rodneCislo_WHEN_existsByRodneCislo_THEN_repository_called() {
        // Given
        String rodneCislo = "9001011234";

        // When
        poistenecRepository.existsByRodneCislo(rodneCislo);

        // Then
        verify(poistenecRepository).existsByRodneCislo(rodneCislo);
    }

    @Test
    void GIVEN_email_WHEN_existsByEmail_THEN_repository_called() {
        // Given
        String email = "test@example.com";

        // When
        poistenecRepository.existsByEmail(email);

        // Then
        verify(poistenecRepository).existsByEmail(email);
    }
}
