package me.nort3x.quanta.internal.utils;

public class TestUtils {
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X ", b));
        }
        return hexString.toString().toUpperCase();
    }
}
