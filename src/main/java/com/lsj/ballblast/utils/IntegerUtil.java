package com.lsj.ballblast.utils;

import java.math.BigDecimal;

public class IntegerUtil {
    /**
     * int 转 float 保留两位小数
     * @param i
     * @return
     */
    public static float intToFloat(int i) {
        String	format = new BigDecimal(String.valueOf(i)).divide(new BigDecimal(100)).toString();
       return Float.valueOf(format);
    }
}
