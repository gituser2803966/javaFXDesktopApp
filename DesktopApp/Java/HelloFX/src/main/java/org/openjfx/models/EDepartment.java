package org.openjfx.models;

public enum EDepartment {
    PRODUCTION("Production"),
    CS("cs"),
    BOD("BOD"),
    FINANCE("Finance"),
    CREATIVE("Creative");

    private String name;

    public String getName() {
        return name;
    }

    EDepartment(String name) {
        this.name = name;
    }
}
