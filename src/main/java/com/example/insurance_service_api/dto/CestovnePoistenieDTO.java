package com.example.insurance_service_api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class CestovnePoistenieDTO extends ZmluvaDTO {
    private LocalDate zaciatokPoistenia;
    private LocalDate koniecPoistenia;
    private boolean poistenieZodpovednosti;
    private boolean poistenieUrazu;
}