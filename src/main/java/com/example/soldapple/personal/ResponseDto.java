package com.example.soldapple.personal;

public class ResponseDto<T> {
    private T data;

    public static <T> ResponseDto<T> result(T data){
        return ResponseDto.result(data);
    }

}
