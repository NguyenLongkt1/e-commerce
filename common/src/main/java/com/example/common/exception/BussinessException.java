package com.example.common.exception;

public class BussinessException extends Exception{
    private String description;
    private Integer code;

    public BussinessException(){}

    public BussinessException(String description,Integer code){
        this.description = description;
        this.code = code;
    }
}
