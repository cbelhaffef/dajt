package com.cbelhaffef.dajt.enums;

public enum FolderColumnImport {

    NUMBER(0),
    DIRECTION_NUMBER(1),
    VICTIMS(2),
    GUILTIES(3),
    OFFENCE(4),
    COURT(5),
    SENDING_TYPE(6);

    private int value;

    FolderColumnImport(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
