package com.example.insurance_service_api.repository;


import com.example.insurance_service_api.model.Poistenec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PoistenecRepository extends JpaRepository<Poistenec, Long> {

    boolean existsByRodneCislo(String rodneCislo);
}
