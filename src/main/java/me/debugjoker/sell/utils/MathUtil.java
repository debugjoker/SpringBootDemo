package me.debugjoker.sell.utils;

/**
 * @author: ZhangMengwei
 * @create: 2019-04-20 13:52
 **/

public class MathUtil {

    public static final Double MONEY_RANGE = 0.01;

    /**
     * 判断两个金额是否相等
     */
    public static Boolean equals(Double d1, Double d2) {
        // 如果两个值相差在给定的范围内就算两者相等
        Double result = Math.abs(d1 - d2);
        if (result < MONEY_RANGE) {
            return true;
        } else {
            return false;
        }
    }
}