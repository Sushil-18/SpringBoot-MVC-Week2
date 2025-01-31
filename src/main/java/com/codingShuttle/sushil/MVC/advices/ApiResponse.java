package com.codingShuttle.sushil.MVC.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ApiResponse<T> {
    @JsonFormat(pattern = "hh:mm:ss dd-MM-YYYY")
    private LocalDateTime timeStamp;

    private T data;

    private ApiError error;

    public ApiResponse(T data){
        this();
        this.data = data;
    }

    public ApiResponse( ){
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(ApiError error){
        this();
        this.error = error;
    }
}
