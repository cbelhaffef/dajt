package com.app.enums;

public enum AffairStatus {
    OPEN("مفتوحة"),
    IN_PROGRESS("جارية"),
    CLOSE("مغلوقة");

    private String name;

    AffairStatus(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
