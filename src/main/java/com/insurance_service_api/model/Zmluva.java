package com.insurance_service_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Zmluva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cisloZmluvy;

    @Column(nullable = false)
    private LocalDate datumVzniku;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Poistenec poistenec;
}