package com.wei.util;

import java.util.Random;

public class MyRandomCode {

    // Math.random() 生成 0.0--1.0 之间的随机数；
    // 如果直接 Math.random()*1000000，会生成随机6位数，也可能会生成5位4位。（因为可能生成0.003这样的数）；
    // (Math.random()*9+1) 一定是大于 1 小于 10 的；Math.random()*9+1)*100000 则一定大于 100000 小于
    // 1000000；
    public static String random01() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }

    // random.nextInt(10) 生成 0-9 之间的随机数；
    // 生成6次，拼接成字符串
    public static String random02() {
        Random random = new Random();
        String result = "";
        for (int j = 0; j < 6; j++) {
            result += random.nextInt(10);
        }
        return result;
    }

    // random.nextInt(999999) 生成 0-999999 之间的随机数；
    // 再加上100000 可得到 100000-999999 之间的随机数
    public static String random03() {
        Random random = new Random();
        int num = random.nextInt(999999);
        if (num < 100000) {
            num += 100000;
        }
        return String.valueOf(num);
    }

    // 这种方法的思路是在一个指定的字符串内随机生成一个子字符串；
    public static String random04() {
        // String sources = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //
        // 数字加上一些字母，就可以生成6位的验证码
        String sources = "0123456789"; // 数字加上一些字母，就可以生成6位的验证码
        Random random = new Random();
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < 6; j++) {
            // flag.append(sources.charAt(random.nextInt(36)) + "");
            flag.append(sources.charAt(random.nextInt(9)) + "");
        }
        return String.valueOf(flag);
    }

}