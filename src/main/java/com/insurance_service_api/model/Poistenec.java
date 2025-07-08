package com.insurance_service_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Poistenec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String meno;

    @Column(nullable = false)
    private String priezvisko;

    @Column(nullable = false, unique = true)
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

    @OneToMany(mappedBy = "poistenec", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zmluva> zmluvy;
}

