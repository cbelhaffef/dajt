package com.app.enums;

public enum FolderStatus {
    OPEN("مفتوح"),
    IN_PROGRESS("جاري"),
    CLOSE("مغلوق");

    private String name;

    FolderStatus(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
