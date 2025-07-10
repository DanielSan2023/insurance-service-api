package com.example.insurance_service_api.repository;

import com.example.insurance_service_api.model.Zmluva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZmluvaRepository extends JpaRepository<Zmluva, Long> {
    Optional<Zmluva> findByCisloZmluvy(String cisloZmluvy);
}
