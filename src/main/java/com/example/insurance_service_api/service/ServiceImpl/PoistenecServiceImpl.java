package com.example.insurance_service_api.service.ServiceImpl;

import com.example.insurance_service_api.dto.*;
import com.example.insurance_service_api.model.Poistenec;
import com.example.insurance_service_api.repository.PoistenecRepository;
import com.example.insurance_service_api.utility.PoistenecConstants;
import com.example.insurance_service_api.validator.PoistenecValidator;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PoistenecServiceImpl implements PoistenecService {

    private final ModelMapper modelMapper;
    private final PoistenecRepository poistenecRepository;
    private final PoistenecValidator poistenecValidator;

    public PoistenecServiceImpl(ModelMapper modelMapper, PoistenecRepository poistenecRepository, PoistenecValidator poistenecValidator) {
        this.modelMapper = modelMapper;
        this.poistenecRepository = poistenecRepository;
        this.poistenecValidator = poistenecValidator;
    }


    @Override
    public List<PoistenecListDto> getAllPoistenci() {
        List<Poistenec> poistenci = poistenecRepository.findAll(Sort.by(Sort.Direction.ASC, PoistenecConstants.SORT_BY_PRIEZVISKO));

        poistenecValidator.validatePoistenciExist(poistenci);

        return poistenci.stream()
                .map(poistenec -> modelMapper.map(poistenec, PoistenecListDto.class))
                .toList();
    }

    @Override
    @Transactional
    public PoistenecDtoIdentifikator createPoistenec(PoistenecCreateDto dto) {
        Poistenec poistenec = modelMapper.map(dto, Poistenec.class);

        poistenecValidator.checkForDuplicateRodneCislo(poistenec.getRodneCislo());
        poistenecValidator.checkIfKorespondencnaAdresaIsNull(poistenec);

        poistenecRepository.save(poistenec);
        return new PoistenecDtoIdentifikator(poistenec.getId());
    }

    @Override
    public PoistenecFullDto getPoistenecById(Long id) {
        Poistenec poistenec = poistenecRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PoistenecConstants.POISTENEC_NOT_FOUND_BY_ID + id));

        poistenecValidator.checkIfKorespondencnaAdresaIsNull(poistenec);

        PoistenecFullDto dto = mapPositenecFullDto(poistenec);
        dto.setZmluvy(poistenec.getZmluvy());     //TODO mapper Zmluva to ZmluvaDTO

        return dto;
    }

    private PoistenecFullDto mapPositenecFullDto(Poistenec poistenec) {
        PoistenecFullDto dto = new PoistenecFullDto();
        dto.setId(poistenec.getId());
        dto.setMeno(poistenec.getMeno());
        dto.setPriezvisko(poistenec.getPriezvisko());
        dto.setRodneCislo(poistenec.getRodneCislo());
        dto.setEmail(poistenec.getEmail());

        dto.setAdresaTrvalehoPobytu(modelMapper.map(poistenec.getAdresaTrvalehoPobytu(), AdresaDto.class));
        dto.setKorespondencnaAdresa(modelMapper.map(poistenec.getKorespondencnaAdresa(), AdresaDto.class));
        return dto;
    }
}
