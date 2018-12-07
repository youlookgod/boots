package com.training.other;

/**
 * george 2018/12/6 14:37
 */
public class TestString {
    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "a", "b", "c", "d", "e", "f"};

    public static void main(String[] args) {
        byte[] bytes = {12, 16};
        String s = byteArrayToHexString(bytes);
        System.out.println(s);
    }

    public static String byteToHexString(byte bytes) {
        int n = bytes;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(byteToHexString(b));
        }
        return sb.toString();
    }
}
