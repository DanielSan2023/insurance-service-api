package com.example.insurance_service_api.repository;

import com.example.insurance_service_api.model.PoistenieMajetku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoistenieMajetkuRepository extends JpaRepository<PoistenieMajetku, Long> {
    // TODO findByCisloZmluvy(String cisloZmluvy) {
}
