package com.snxy.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {

    private MD5Util() {

    }

    public static String encrypt(String plainText) {
        if (StringUtil.isBlank(plainText)) {
            return null;
        }
        return md5(plainText);
    }


    private static String md5(String plainText) {
        String result = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(plainText.getBytes());
            byte[] bytes = md5.digest();
            result = bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (byte aByte : bytes) {
            digital = aByte;
            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }

    public static void main(String[] args) {
       String key =  encrypt("111111");

       System.out.println("key : "+key);
       System.out.println(System.currentTimeMillis());

    }

}
