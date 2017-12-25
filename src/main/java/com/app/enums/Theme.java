package com.app.enums;

public enum Theme {

    INSULT("إهانة");

    private String name = "";

    Theme(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
