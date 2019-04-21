package me.debugjoker.sell.utils;

import me.debugjoker.sell.enums.CodeEnum;

/**
 * @author: ZhangMengwei
 * @create: 2019-04-21 18:40
 * 枚举工具类
 **/

public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (each.getCode().equals(code)) {
                return each;
            }
        }
        return null;
    }
}