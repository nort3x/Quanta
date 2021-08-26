package me.nort3x.quanta.internal.utils;

import java.util.List;

public class TestUtils {
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X ", b));
        }
        return hexString.toString().toUpperCase();
    }

    public static double average(List<Long> items){
        double ans = 0;
        for (Long item : items) {
            ans+=item;
        }
        return ans/items.size();
    }
}
