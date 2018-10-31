package com.lsj.ballblast.utils;

import com.google.common.base.Strings;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * 描述
 * Created by lsj on 2018/3/13 10:46
 */
@Log4j2
public class MD5Util {
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString=null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (Strings.isNullOrEmpty(charsetname)){
                resultString = byteArrayToHexString(md.digest(origin.getBytes(Charset.forName("UTF-8"))));
            }
            else {
                resultString = byteArrayToHexString(md.digest(origin
                        .getBytes(charsetname)));
            }
        } catch (Exception exception) {
            log.error("MD5Util.MD5Encode exception:{}",exception);
        }
        return resultString;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
}
