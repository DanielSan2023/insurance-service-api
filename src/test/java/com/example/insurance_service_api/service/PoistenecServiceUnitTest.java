package com.example.insurance_service_api.service;

import com.example.insurance_service_api.dto.*;
import com.example.insurance_service_api.model.Adresa;
import com.example.insurance_service_api.model.Poistenec;
import com.example.insurance_service_api.repository.PoistenecRepository;
import com.example.insurance_service_api.service.ServiceImpl.PoistenecServiceImpl;
import com.example.insurance_service_api.utility.PoistenecConstants;
import com.example.insurance_service_api.validator.PoistenecValidator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PoistenecServiceUnitTest {

    @Mock
    private PoistenecRepository poistenecRepository;

    @Mock
    private PoistenecValidator poistenecValidator;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PoistenecServiceImpl poistenecService;

    @Test
    void GIVEN_valid_poistenecCreateDto_WHEN_createPoistenec_THEN_save_and_return_id() {
        // Given
        PoistenecCreateDto dto = PoistenecCreateDto.builder()
                .meno("Jozef")
                .priezvisko("Mrkvička")
                .rodneCislo("9001011234")
                .email("jozef@example.com")
                .adresaTrvalehoPobytu(new AdresaDto())
                .build();

        Poistenec poistenec = new Poistenec();
        poistenec.setId(123L);
        poistenec.setMeno("Jozef");
        poistenec.setPriezvisko("Mrkvička");
        poistenec.setRodneCislo("9001011234");
        poistenec.setEmail("jozef@example.com");
        poistenec.setAdresaTrvalehoPobytu(new Adresa());

        when(modelMapper.map(dto, Poistenec.class)).thenReturn(poistenec);

        // When
        PoistenecDtoIdentifikator result = poistenecService.createPoistenec(dto);

        // Then
        verify(poistenecValidator).checkForDuplicateRodneCislo("9001011234");
        verify(poistenecValidator).checkForDuplicateEmail("jozef@example.com");
        verify(poistenecValidator).checkIfKorespondencnaAdresaIsNull(poistenec);
        verify(poistenecRepository).save(poistenec);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(123L);
    }

    @Test
    void GIVEN_existing_poistenci_WHEN_getAllPoistenci_THEN_return_mapped_list() {
        // Given
        Poistenec p = new Poistenec();
        List<Poistenec> entityList = List.of(p);

        when(poistenecRepository.findAll(Sort.by(Sort.Direction.ASC, PoistenecConstants.SORT_BY_PRIEZVISKO)))
                .thenReturn(entityList);
        doNothing().when(poistenecValidator).validatePoistenciExist(entityList);
        doNothing().when(poistenecValidator).checkIfKorespondencnaAdresaIsNull(p);
        when(modelMapper.map(p, PoistenecListDto.class)).thenReturn(new PoistenecListDto());

        // When
        List<PoistenecListDto> result = poistenecService.getAllPoistenci();

        // Then
        assertThat(result).hasSize(1);
        verify(poistenecRepository).findAll(any(Sort.class));
        verify(poistenecValidator).validatePoistenciExist(entityList);
        verify(poistenecValidator).checkIfKorespondencnaAdresaIsNull(p);
    }

    @Test
    void GIVEN_valid_id_WHEN_getPoistenecById_THEN_return_full_dto() {
        // Given
        Long id = 1L;
        Poistenec poistenec = new Poistenec();
        poistenec.setId(id);
        poistenec.setAdresaTrvalehoPobytu(new Adresa());

        when(poistenecRepository.findById(id)).thenReturn(Optional.of(poistenec));
        doNothing().when(poistenecValidator).checkIfKorespondencnaAdresaIsNull(poistenec);
        when(modelMapper.map(any(Adresa.class), eq(AdresaDto.class))).thenReturn(new AdresaDto());

        // When
        PoistenecFullDto result = poistenecService.getPoistenecById(id);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        verify(poistenecRepository).findById(id);
        verify(poistenecValidator).checkIfKorespondencnaAdresaIsNull(poistenec);
    }

    @Test
    void GIVEN_invalid_id_WHEN_getPoistenecById_THEN_throw_exception() {
        // Given
        Long id = 999L;
        when(poistenecRepository.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> poistenecService.getPoistenecById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(PoistenecConstants.POISTENEC_NOT_FOUND_BY_ID + id);
    }
}