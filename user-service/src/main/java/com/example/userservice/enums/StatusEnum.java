package com.example.userservice.enums;

public enum StatusEnum {
    LOCK(0),
    ACTIVE(1);

    final int value;

    StatusEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
