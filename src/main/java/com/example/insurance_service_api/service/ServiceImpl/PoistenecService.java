package com.example.insurance_service_api.service.ServiceImpl;

import com.example.insurance_service_api.dto.PoistenecCreateDto;
import com.example.insurance_service_api.dto.PoistenecDtoIdentifikator;
import com.example.insurance_service_api.dto.PoistenecFullDto;
import com.example.insurance_service_api.dto.PoistenecListDto;

import java.util.List;

public interface PoistenecService {

    List<PoistenecListDto> getAllPoistenci();

    PoistenecDtoIdentifikator createPoistenec(PoistenecCreateDto dto);

    PoistenecFullDto getPoistenecById(Long id);
}
