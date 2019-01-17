package com.cbelhaffef.dajt.dao.enums;

public enum FolderColumnImportEnum {

    KEY(0),
    DIRECTORATE_KEY(1),
    VICTIMS(2),
    ACCUSED(3),
    OFFENCE(4),
    COURT(5),
    ADMINSTRATION_CONCERNED(6);

    private int value;

    FolderColumnImportEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
