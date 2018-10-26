package com.lsj.ballblast.utils;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * 描述
 *
 * @author Y.S.K on 2018/5/16 14:42
 */
public class StringUtil {
    public static final String COMMA = ",";
    public static final String EMPTY = "";
    public static final String SIGN = "sign";
    public static final String TYPE = "type";
    public static final String DATA = "data";
    public static final String UTF8 = "UTF-8";
    public static final String EMPTY_ARRAY = "[]";
    public static final String UNDEFINED = "undefined";
    public static final String ZERO = "0";
    private static final int ORDER_RANDOM = 1000;
    public static final String LOGIN_TOKEN = "token";
    public static final String BLANK_TEXT = " ";
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String UNDERLINE="_";

    /**
     * 获取随机数
     *
     * @param length 随机数长度
     * @return
     */
    public static String getRandomString(int length) {
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    public static String createOrderId() {
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDateTime.now().toString().replaceAll("[^\\d]+", ""));
        String ran = String.valueOf(new Random().nextInt(ORDER_RANDOM));
        for (int i = ran.length(); i < String.valueOf(ORDER_RANDOM).length() - 1; i++) {
            sb.append(StringUtil.ZERO);
        }
        return sb.append(ran).toString();
    }

    public static Boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }

}
