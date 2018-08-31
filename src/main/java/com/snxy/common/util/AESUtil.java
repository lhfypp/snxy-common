package com.snxy.common.util;

import com.snxy.common.exception.BizException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class AESUtil {

    private static final String AES_ALG = "AES";

    /**
     * AES算法
     */
    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";

    private static final byte[] AES_IV = initIv(AES_CBC_PCK_ALG);

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 加密
     *
     * @param content
     * @param key
     * @return
     * @throws BizException
     */
    public static String encryptContent(String content, String key) throws BizException {

        return aesEncrypt(content, key, DEFAULT_CHARSET);
    }

    /**
     * 解密
     *
     * @param content
     * @param key
     * @return
     * @throws BizException
     */
    public static String decryptContent(String content, String key) throws BizException {

        return aesDecrypt(content, key, DEFAULT_CHARSET);
    }

    /**
     * AES加密
     *
     * @param content
     * @param aesKey
     * @param charset
     * @return
     * @throws BizException
     */
    private static String aesEncrypt(String content, String key, String charset)
            throws BizException {

        try {
            byte[] bytes = initKey(key);
            String encryptKey = Base64.encodeBase64String(bytes);
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);

            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(Base64.decodeBase64(encryptKey.getBytes()), AES_ALG), iv);

            byte[] encryptBytes = cipher.doFinal(content.getBytes(charset));

            return StringUtil.byte2HexStr(encryptBytes);
        } catch (Exception e) {
            throw new BizException("AES加密失败：Aescontent = " + content + "; DEFAULT_CHARSET = "
                    + charset, e);
        }

    }

    /**
     * AES解密
     *
     * @param content
     * @param key
     * @param charset
     * @return
     * @throws BizException
     */
    private static String aesDecrypt(String content, String key, String charset)
            throws BizException {
        try {
            byte[] bytes = initKey(key);
            String encryptKey  = Base64.encodeBase64String(bytes);
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(initIv(AES_CBC_PCK_ALG));
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(encryptKey.getBytes()),
                    AES_ALG), iv);

            byte[] cleanBytes = cipher.doFinal(StringUtil.hexStr2Byte(content));
            return new String(cleanBytes, charset);
        } catch (Exception e) {
            throw new BizException("AES解密失败：Aescontent = " + content + "; DEFAULT_CHARSET = "
                    + charset, e);
        }
    }

    /**
     * 初始向量的方法, 全部为0. 这里的写法适合于其它算法,针对AES算法的话,IV值一定是128位的(16字节).
     */
    private static byte[] initIv(String fullAlg) {

        try {
            Cipher cipher = Cipher.getInstance(fullAlg);
            int blockSize = cipher.getBlockSize();
            byte[] iv = new byte[blockSize];
            for (int i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }
            return iv;
        } catch (Exception e) {

            int blockSize = 16;
            byte[] iv = new byte[blockSize];
            for (int i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }
            return iv;
        }
    }

    private static byte[] initKey(String key) {
        // 生成密钥
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(AES_ALG);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        keyGenerator.init(128, new SecureRandom(key.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    public static void main(String[] args) {

      //  String content = "100005-4-1524801667846";
        String content = "TOKEN-15001080031-1-"+System.currentTimeMillis();
        String key = "这是第一次ahahh12234_,er2aflal";
        byte[] bytes = initKey(key);
    //    String aes = encryptContent(content, Base64.encodeBase64String(bytes));
        String aes = encryptContent(content,key);
        System.out.println(aes);

    //    String dec = decryptContent(aes, Base64.encodeBase64String(bytes));
        String dec = decryptContent(aes,key);
        System.out.println(dec);

    }

}
