package org.openjfx.models;

public enum EConstructionContent {

    NEW("NEW"),
    CHANGE_LAYOUT("CHANGE_LAYOUT"),
    //phát sinh
    ARISEMORE("ARISEMORE"),
    //sữa chữa
    REPAIR("REPAIR"),
    //tháo dỡ
    DISMANTLING("DISMANTLING");

    private String name;

    public String getName() {
        return name;
    }

    EConstructionContent(String name) {
        this.name = name;
    }

}
