package com.example.insurance_service_api.validator;

import com.example.insurance_service_api.model.Poistenec;
import com.example.insurance_service_api.repository.PoistenecRepository;
import com.example.insurance_service_api.utility.PoistenecConstants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PoistenecValidator {
    private final PoistenecRepository poistenecRepository;

    public PoistenecValidator(PoistenecRepository poistenecRepository) {
        this.poistenecRepository = poistenecRepository;
    }
//    public void validateRodneCislo(String rodneCislo) {
//        if (!rodneCislo.matches(PoistenecConstants.RODNE_CISLO_REGEX)) {
//            throw new IllegalArgumentException(PoistenecConstants.RODNE_CISLO_ERROR_MESSAGE);
//        }

    public void checkForDuplicateRodneCislo(String rodneCislo) {
        if (poistenecRepository.existsByRodneCislo(rodneCislo)) {
            throw new IllegalArgumentException(PoistenecConstants.DUPLICATE_RODNE_CISLO_EXCEPTION);
        }
    }

    public void checkIfKorespondencnaAdresaIsNull(Poistenec poistenec) {
        if (poistenec.getKorespondencnaAdresa() == null) {
            poistenec.setKorespondencnaAdresa(poistenec.getAdresaTrvalehoPobytu());
        }
    }

    public void validatePoistenciExist(List<Poistenec> poistenci) {
        if (poistenci.isEmpty()) {
            throw new EntityNotFoundException(PoistenecConstants.POISTENCI_NOT_FOUND);
        }
    }
}
