package com.lsj.ballblast.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;

/**
 * 描述
 *
 * @author Y.S.K
 * @date 2018/8/8 18:31
 */
public class AESUtil {
    private static boolean INITIALIZED = false;
    private final static String AES = "AES";

    public static String aesDecryptByBytes(String encryptedData, String sessionKey, String iv) {
        initialize();

        try {
            byte[] content = Base64.decodeBase64(encryptedData.getBytes(StringUtil.UTF8));
            byte[] keyByte = Base64.decodeBase64(sessionKey.getBytes(StringUtil.UTF8));
            byte[] ivByte = Base64.decodeBase64(iv.getBytes(StringUtil.UTF8));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec sKeySpec = new SecretKeySpec(keyByte, AES);

            AlgorithmParameters params = AlgorithmParameters.getInstance(AES);
            params.init(new IvParameterSpec(ivByte));

            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, params);
            byte[] decryptBytes = cipher.doFinal(content);
            if (null != decryptBytes && decryptBytes.length > 0) {
                String result = new String(decryptBytes, StringUtil.UTF8);
                return result;
            }
            return null;
        } catch (NoSuchAlgorithmException e) {
            return e.toString();
        } catch (InvalidKeyException e) {
            return e.toString();
        } catch (InvalidAlgorithmParameterException e) {
            return e.toString();
        } catch (NoSuchPaddingException e) {
            return e.toString();
        } catch (BadPaddingException e) {
            return e.toString();
        } catch (UnsupportedEncodingException e) {
            return e.toString();
        } catch (InvalidParameterSpecException e) {
            return e.toString();
        } catch (IllegalBlockSizeException e) {
            return e.toString();
        } catch (NoSuchProviderException e) {
            return e.toString();
        }
    }

    public static void initialize() {
        if (INITIALIZED) {
            return;
        }
        Security.addProvider(new BouncyCastleProvider());
        INITIALIZED = true;
    }

    public static void main(String[] args) throws Exception {

        String encryptedData = "W5jLp7pyk7HWnbk9OIh1bE8dAKc8wUYBpujpUx2CDILUGQx8hSO7MmKWbrvfLSvoZcEL/Id22X9/Nl/9J7wjqVT2q3VR8ypsQOX0a9ydAQk/ohPfbqq0YYwCoGkS3Gf/LKvaNczEQA1XuTXDZkL6NXFUirmihSbvq/tidVTGeRbC KuM4 frtZRzTb5c6yvqpN6seSS8ZdjUxwMOO/3ze4RwX3jlfMpYIRqwgc19D Vn 3uYKYzk bhN7WrVvxyCA85kaSkVDR67AzPa5bkgG hs7IpPkyB0c7P3YfQH5qcR5zna1xxwmWdSTq6nxE9lsFDdZMlafeeghZA46O8ysxBTsOnTjbjoqfZgQDa5cleuXum/ qeNAlBhIQyhcn1TL3nfSJhlDWxC/qicSqmyANKfG/8vP2fZGq/v/R8gPXx8FMMDDWo2g9ugWdfsaCnO8qWPIu1xBkmO/XBm8 Gys7qtv556GeySBtS7m EKIcd zAFgy1xf1DQrrnUxu6DcXQ1iT5MCS7xlVRW72SThNQ==";
        String iv = "0A0nYs  k5RNFiIg MFB Q==";
        String sessionKey = "Ul2qKVEi0dZha5irsQKdmw==";

        iv = iv.replaceAll(" ", "+");
        encryptedData = encryptedData.replaceAll(" ", "+");
        sessionKey = sessionKey.replaceAll(" ", "+");

        System.out.println("aesde=" + aesDecryptByBytes(encryptedData, sessionKey, iv));
    }
}