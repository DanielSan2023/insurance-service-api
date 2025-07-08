package com.example.insurance_service_api.controller;


import com.example.insurance_service_api.dto.PoistenecCreateDto;
import com.example.insurance_service_api.dto.PoistenecFullDto;
import com.example.insurance_service_api.dto.PoistenecListDto;
import com.example.insurance_service_api.dto.PoistenecDtoIdentifikator;
import com.example.insurance_service_api.service.ServiceImpl.PoistenecService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insured")
public class PoistenecController {

    private final PoistenecService poistenecService;

    public PoistenecController(PoistenecService poistenecService) {
        this.poistenecService = poistenecService;
    }

    @GetMapping
    public ResponseEntity<List<PoistenecListDto>> getAll() {
        List<PoistenecListDto> poistenci = poistenecService.getAllPoistenci();
        return ResponseEntity.ok(poistenci);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid PoistenecCreateDto dto) {
        PoistenecDtoIdentifikator poistenec = poistenecService.createPoistenec(dto);
        return ResponseEntity.ok(poistenec.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PoistenecFullDto> getById(@PathVariable Long id) {
        PoistenecFullDto poistenec = poistenecService.getPoistenecById(id);
        return ResponseEntity.ok(poistenec);
    }
}
