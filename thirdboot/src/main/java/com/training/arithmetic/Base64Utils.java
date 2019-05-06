package com.training.arithmetic;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Base64加密算法
 * Created by louis.wei on 2017/5/27.
 */
public class Base64Utils {

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final char LAST2BYTE = (char) Integer.parseInt("00000011", 2);
    private static final char LAST4BYTE = (char) Integer.parseInt("00001111", 2);
    private static final char LAST6BYTE = (char) Integer.parseInt("00111111", 2);
    private static final char LEAD6BYTE = (char) Integer.parseInt("11111100", 2);
    private static final char LEAD4BYTE = (char) Integer.parseInt("11110000", 2);
    private static final char LEAD2BYTE = (char) Integer.parseInt("11000000", 2);
    private static final char[] ENCODE_TABLE = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    public static String doPasswordDigest(String nonce, String created, String privateKey) {
        String passwordDigest = null;
        try {
            byte[] b1 = nonce != null ? nonce.getBytes("UTF-8") : new byte[0];
            byte[] b2 = created != null ? created.getBytes("UTF-8") : new byte[0];
            byte[] b3 = privateKey != null ? privateKey.getBytes("UTF-8") : new byte[0];
            byte[] b4 = new byte[b1.length + b2.length + b3.length];
            int offset = 0;
            System.arraycopy(b1, 0, b4, offset, b1.length);
            offset += b1.length;
            System.arraycopy(b2, 0, b4, offset, b2.length);
            offset += b2.length;
            System.arraycopy(b3, 0, b4, offset, b3.length);
            byte[] digestBytes = generateDigest(b4);
            passwordDigest = Base64Utils.base64Encode(digestBytes);
        } catch (Exception e) {
            //logger.error(e);
        }
        return passwordDigest;
    }

    public static byte[] generateDigest(byte[] inputBytes) throws Exception {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            return digest.digest(inputBytes);
        } catch (Exception e) {
            throw new Exception("Error in generating digest", e);
        }
    }

    public static String base64Encode(byte[] from) {
        StringBuffer to = new StringBuffer((int) (from.length * 1.34) + 3);
        int num = 0;
        char currentByte = 0;
        for (int i = 0; i < from.length; i++) {
            num = num % 8;
            while (num < 8) {
                switch (num) {
                    case 0:
                        currentByte = (char) (from[i] & LEAD6BYTE);
                        currentByte = (char) (currentByte >>> 2);
                        break;
                    case 2:
                        currentByte = (char) (from[i] & LAST6BYTE);
                        break;
                    case 4:
                        currentByte = (char) (from[i] & LAST4BYTE);
                        currentByte = (char) (currentByte << 2);
                        if ((i + 1) < from.length) {
                            currentByte |= (from[i + 1] & LEAD2BYTE) >>> 6;
                        }
                        break;
                    case 6:
                        currentByte = (char) (from[i] & LAST2BYTE);
                        currentByte = (char) (currentByte << 4);
                        if ((i + 1) < from.length) {
                            currentByte |= (from[i + 1] & LEAD4BYTE) >>> 4;
                        }
                        break;
                }
                to.append(ENCODE_TABLE[currentByte]);
                num += 6;
            }
        }
        if (to.length() % 4 != 0) {
            for (int i = 4 - to.length() % 4; i > 0; i--) {
                to.append("=");
            }
        }
        return to.toString();
    }

    public static String base64Encode(String from) throws UnsupportedEncodingException {
        return base64Encode(from.getBytes(DEFAULT_ENCODING));
    }

}
