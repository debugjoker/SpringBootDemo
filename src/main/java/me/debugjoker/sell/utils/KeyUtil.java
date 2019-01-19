package me.debugjoker.sell.utils;

import java.util.Random;

/**
 * @author: ZhangMengwei
 * @create: 2019-01-19 20:01
 **/

public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     * 保证多线程时主键的唯一性
     **/
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer a = random.nextInt(900000) + 100000; // 生成6位随机数
        return System.currentTimeMillis() + String.valueOf(a);
    }
}