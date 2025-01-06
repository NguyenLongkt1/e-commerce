package com.example.categoryservice.exception;

import lombok.Data;

@Data
public class BussinessException extends Exception{
    private String description;
    private Integer code;

    public BussinessException(){}

    public BussinessException(String description,Integer code){
        this.description = description;
        this.code = code;
    }
}
