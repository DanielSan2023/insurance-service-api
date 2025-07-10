package com.example.insurance_service_api.utility;

public class PoistenecConstants {
    public static final String RODNE_CISLO_REGEX = "^(\\d{6}[A-Z]\\d{3})$";
    public static final String POISTENEC_NOT_FOUND = "Poistenec s danym ID nebol najdeny.";
    public static final String ENTITY_NOT_FOUND = "Entita nebola najdena.";

    public static final String DUPLICATE_RODNE_CISLO_EXCEPTION = "Poistenec s rodným číslom %s už existuje.";
    public static final String DUPLICATE_EMAIL_ADDRESS_EXCEPTION = "Poistenec s emailovou adresou %s už existuje.";
    public static final String POISTENEC_NOT_FOUND_BY_ID = "Poistenec s ID %d nebol najdeny.";
    public static final String POISTENCI_NOT_FOUND = "V database nie su ziadni poistenci.";
    public static final String BAD_REQUEST = "Bad request";

    public static final String SORT_BY_PRIEZVISKO = "priezvisko";
}
