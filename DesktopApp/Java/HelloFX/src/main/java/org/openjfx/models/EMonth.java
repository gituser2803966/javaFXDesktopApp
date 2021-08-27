package org.openjfx.models;

public enum EMonth {

    Jan("1"),
    Feb("Feb"),
    Mar("Mar"),
    Apr("Apr"),
    May("May"),
    Jun("Jun"),
    Jul("Jul"),
    Aug("Aug"),
    Sep("Sep"),
    Oct("Oct"),
    Nov("Nov"),
    Dec("Dec");

    private String name;

    public String getName() {
        return name;
    }

    EMonth(String name) {
        this.name = name;
    }

}
