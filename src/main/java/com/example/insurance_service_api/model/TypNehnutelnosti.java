package com.example.insurance_service_api.model;

public enum TypNehnutelnosti {

    BYT("BYT"),
    RODINNY_DOM_MUROVANY("RODINNY_DOM_MUROVANY"),
    RODINNY_DOM_DREVENY("RODINNY_DOM_DREVENY");

    private final String popis;

    TypNehnutelnosti(String popis) {
        this.popis = popis;
    }

    public String getPopis() {
        return popis;
    }

    @Override
    public String toString() {
        return popis;
    }
}

