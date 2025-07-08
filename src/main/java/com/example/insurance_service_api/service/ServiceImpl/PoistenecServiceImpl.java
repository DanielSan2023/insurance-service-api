package com.example.insurance_service_api.service.ServiceImpl;


import com.example.insurance_service_api.dto.*;
import com.example.insurance_service_api.model.Poistenec;
import com.example.insurance_service_api.repository.PoistenecRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PoistenecServiceImpl implements PoistenecService {

    private final ModelMapper modelMapper;
    private final PoistenecRepository poistenecRepository;

    public PoistenecServiceImpl(ModelMapper modelMapper, PoistenecRepository poistenecRepository) {
        this.modelMapper = modelMapper;
        this.poistenecRepository = poistenecRepository;
    }


    @Override
    public List<PoistenecListDto> getAllPoistenci() {
        List<Poistenec> poistenci = poistenecRepository.findAll(Sort.by(Sort.Direction.ASC, "priezvisko"));

        if (poistenci.isEmpty()) {
            throw new RuntimeException("V database nie su ziadni poistenci.");
        }

        return poistenci.stream()
                .map(poistenec -> modelMapper.map(poistenec, PoistenecListDto.class))
                .toList();
    }

    @Override
    @Transactional
    public PoistenecDtoIdentifikator createPoistenec(PoistenecCreateDto dto) {
        Poistenec poistenec = modelMapper.map(dto, Poistenec.class);

        if (poistenec.getKorespondencnaAdresa() == null) {
            poistenec.setKorespondencnaAdresa(poistenec.getAdresaTrvalehoPobytu());
        }

        poistenecRepository.save(poistenec);
        return new PoistenecDtoIdentifikator(poistenec.getId());
    }

    @Override //TODO mapper ZmluvaDTO
    public PoistenecFullDto getPoistenecById(Long id) {
        Poistenec poistenec = poistenecRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poistenec s ID " + id + " nebol najdeny."));

        if (poistenec.getKorespondencnaAdresa() == null) {
            poistenec.setKorespondencnaAdresa(poistenec.getAdresaTrvalehoPobytu());
        }

        PoistenecFullDto dto = new PoistenecFullDto();
        dto.setId(poistenec.getId());
        dto.setMeno(poistenec.getMeno());
        dto.setPriezvisko(poistenec.getPriezvisko());
        dto.setRodneCislo(poistenec.getRodneCislo());
        dto.setEmail(poistenec.getEmail());

        dto.setAdresaTrvalehoPobytu(modelMapper.map(poistenec.getAdresaTrvalehoPobytu(), AdresaDto.class));
        dto.setKorespondencnaAdresa(modelMapper.map(poistenec.getKorespondencnaAdresa(), AdresaDto.class));

        dto.setZmluvy(poistenec.getZmluvy());

        return dto;
    }
}
