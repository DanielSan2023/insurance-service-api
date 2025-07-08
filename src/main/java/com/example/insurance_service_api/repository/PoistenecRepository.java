package com.example.insurance_service_api.repository;


import com.example.insurance_service_api.model.Poistenec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PoistenecRepository extends JpaRepository<Poistenec, Long> {

    // Define custom query methods if needed
    // For example, to find a Poistenec by their identification number:
    // Optional<Poistenec> findByIdentificationNumber(String identificationNumber);

    // You can also define methods for checking existence or other criteria
    // boolean existsByIdentificationNumber(String identificationNumber);
}
