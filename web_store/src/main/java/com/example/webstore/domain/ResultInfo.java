package com.example.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultInfo {
    // 成功时的状态码
    public static final int SUCCESS_CODE = 200;
    // 失败时的状态码
    public static final int ERROR_CODE = 500;

    // 返回的消息
    private String message;
    // 返回的数据
    private Object data;
    // 返回的状态码
    private int code;

    public ResultInfo(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 生成成功的返回结果
    public static ResultInfo success(Object data) {
        return new ResultInfo(SUCCESS_CODE, "操作成功", data);
    }

    // 生成失败的返回结果
    public static ResultInfo error(String message) {
        return new ResultInfo(ERROR_CODE, message, null);
    }

}
