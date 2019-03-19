package com.cbelhaffef.dajt.api.folder;

public enum FolderTopicEnum {

    UNKOWN("؟؟؟");

    private String value;

    FolderTopicEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
