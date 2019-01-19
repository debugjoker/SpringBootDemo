package me.debugjoker.sell.vo;

import lombok.Data;

/**
 * 返回给前端的数据封装
 * @author: Mengwei Zhang
 * @create: 2018-08-26 21:43
 **/
@Data
public class ResultVO<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的具体内容
     */
    private T data;
}