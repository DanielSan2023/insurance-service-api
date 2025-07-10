package com.example.insurance_service_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Poistenec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String meno;

    @Column(nullable = false)
    private String priezvisko;

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String rodneCislo;

    @Column(nullable = false, unique = true)
    private String email;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "psc", column = @Column(name = "trvale_psc")),
            @AttributeOverride(name = "obec", column = @Column(name = "trvale_obec")),
            @AttributeOverride(name = "ulica", column = @Column(name = "trvale_ulica")),
            @AttributeOverride(name = "cisloDomu", column = @Column(name = "trvale_cislo_domu"))
    })
    private Adresa adresaTrvalehoPobytu;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "psc", column = @Column(name = "korespondencna_psc")),
            @AttributeOverride(name = "obec", column = @Column(name = "korespondencna_obec")),
            @AttributeOverride(name = "ulica", column = @Column(name = "korespondencna_ulica")),
            @AttributeOverride(name = "cisloDomu", column = @Column(name = "korespondencna_cislo_domu"))
    })
    private Adresa korespondencnaAdresa;

    @OneToMany(mappedBy = "poistenec", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Zmluva> zmluvy = new ArrayList<>();;
}

