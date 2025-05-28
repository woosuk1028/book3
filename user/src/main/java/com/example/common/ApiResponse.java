package com.example.common;

import lombok.RequiredArgsConstructor;

public class ApiResponse<T> {
    private int res_code;
    private String res_msg;
    private T data;

    public ApiResponse(int res_code, String res_msg, T data) {
        this.res_code = res_code;
        this.res_msg = res_msg;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(0, "success", data);
    }

    public static <T> ApiResponse<T> fail(int code, String msg) {
        return new ApiResponse<>(code, msg, null);
    }

}
