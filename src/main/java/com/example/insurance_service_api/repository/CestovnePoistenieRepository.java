package com.example.insurance_service_api.repository;

import com.example.insurance_service_api.model.CestovnePoistenie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CestovnePoistenieRepository extends JpaRepository<CestovnePoistenie, Long> {
    //TODO findByCisloZmluvy(String cisloZmluvy)
}
