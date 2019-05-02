package me.debugjoker.sell.exception;

import me.debugjoker.sell.enums.ResultEnum;

/**
 * @author: ZhangMengwei
 * @create: 2019-01-19 19:46
 **/

public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}