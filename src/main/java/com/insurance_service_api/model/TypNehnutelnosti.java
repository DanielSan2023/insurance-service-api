package com.insurance_service_api.model;

public enum TypNehnutelnosti {

    BYT("Byt"),
    RODINNY_DOM_MUROVANY("Rodinny dom - murovany"),
    RODINNY_DOM_DREVENY("Rodinny dom - dreveny");

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

