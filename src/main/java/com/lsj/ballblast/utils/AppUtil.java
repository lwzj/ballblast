package com.lsj.ballblast.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 *
 * @author Y.S.K
 * @date 2018/9/12 18:01
 */
@Log4j2
public class AppUtil {
    /**
     * 解析channel
     *
     * @param channel
     * @return
     */
    public static String getChannel(String channel) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, Object> map = objectMapper.readValue(channel, Map.class);
            if (map != null && map.get("query") != null && ((HashMap) map.get("query")).get("channel") != null) {
                return ((HashMap) map.get("query")).get("channel").toString();
            }
        } catch (IOException e) {
            log.error("channel: {}", channel);
            e.printStackTrace();
        }
        return "other";
    }

    public static boolean isEven(int num) {
        return num % 2 == 0;
    }


    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes(StringUtil.UTF8));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }


}
