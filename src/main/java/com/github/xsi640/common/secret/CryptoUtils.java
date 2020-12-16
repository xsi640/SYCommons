package com.github.xsi640.common.secret;

import com.github.xsi640.common.Rfc2898DeriveBytes;
import org.apache.commons.codec.binary.Base64;

/**
 * 密码工具类
 *
 * @author Yang
 */
public class CryptoUtils {
    private static int saltSize = 32;
    private static int iterations = 1000;
    private static int subKeySize = 32;

    /**
     * 获取随机生成的Salt
     *
     * @return 随机生成的Salt
     */
    public static String getSalt() {
        return Rfc2898DeriveBytes.generateSalt(saltSize);
    }

    /**
     * 获取hash后的密码
     *
     * @param password 明文密码
     * @param salt     用于生成hash的salt
     * @return 经过密码和salt的密文
     */
    public static String getHash(String password, String salt) {
        Rfc2898DeriveBytes keyGenerator = null;
        try {
            keyGenerator = new Rfc2898DeriveBytes(password + salt, saltSize, iterations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] subKey = keyGenerator.getBytes(subKeySize);
        byte[] bSalt = keyGenerator.getSalt();
        byte[] hashPassword = new byte[1 + saltSize + subKeySize];
        System.arraycopy(bSalt, 0, hashPassword, 1, saltSize);
        System.arraycopy(subKey, 0, hashPassword, saltSize + 1, subKeySize);
        return Base64.encodeBase64String(hashPassword);
    }

    /**
     * 验证密码
     *
     * @param hashedPassword hash过的密码（密文）
     * @param password       输入的密码（明文）
     * @param salt           用于生成密码的salt
     * @return true-密码输入正确，false-密码输入错误
     */
    public static boolean verify(String hashedPassword, String password, String salt) {
        byte[] hashedPasswordBytes = Base64.decodeBase64(hashedPassword);
        if (hashedPasswordBytes.length != (1 + saltSize + subKeySize) || hashedPasswordBytes[0] != 0x00) {
            return false;
        }

        byte[] bSalt = new byte[saltSize];
        System.arraycopy(hashedPasswordBytes, 1, bSalt, 0, saltSize);
        byte[] storedSubkey = new byte[subKeySize];
        System.arraycopy(hashedPasswordBytes, 1 + saltSize, storedSubkey, 0, subKeySize);
        Rfc2898DeriveBytes deriveBytes = null;
        try {
            deriveBytes = new Rfc2898DeriveBytes(password + salt, bSalt, iterations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] generatedSubkey = deriveBytes.getBytes(subKeySize);
        return byteArraysEqual(storedSubkey, generatedSubkey);
    }

    private static boolean byteArraysEqual(byte[] storedSubkey, byte[] generatedSubkey) {
        int size = storedSubkey.length;
        if (size != generatedSubkey.length) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (storedSubkey[i] != generatedSubkey[i]) {
                return false;
            }
        }
        return true;
    }

}
