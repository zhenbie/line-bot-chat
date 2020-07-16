package com.linecorp.bot.spring.boot.common.bean;

import lombok.Data;

/**
 * common response vo
 *
 * @author tianqi
 * @date 2020-5-21
 */
@Data
public class ResponseVo<T> {

    private String code;

    private String msg;

    private T data;

    public ResponseVo() {
    }

    public ResponseVo(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
