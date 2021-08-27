package org.openjfx.models;

public enum ERule {
    EMPLOYEE("employee"),
    ADMIN("admin");

    private final String ruleName;

    public String getName() {
        return ruleName;
    }

     ERule(String ruleName) {
        this.ruleName = ruleName;
    }

}
