package com.fan.server.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KeFan
 * @date 2023/5/5
 * @time 20:05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<T>(200, "操作成功", data);
    }

    public static <T> Result<T> success(T data, String msg) {
        return new Result<T>(200, msg, data);
    }

    public static <T> Result<T> success(String msg) {
        return new Result<T>(200, msg, null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<T>(201, msg, null);
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<T>(code, msg, null);
    }

    public static <T> Result<T> fail(Integer code) {
        return new Result<T>(code, "服务器异常", null);
    }
}
