package com.github.danui.feistel;

public class FeistelUtils {
    private FeistelUtils() {
    }

    static byte[] xor(final byte[] x, final byte[] y) {
        final byte[] result = new byte[x.length];
        for (int i = 0; i < x.length; ++i) {
            result[i] = xor(x[i], y[i]);
        }
        return result;
    }

    static byte xor(final byte x, final byte y) {
        return (byte) (x ^ y);
    }

    static void zeroize(final byte[] bytes) {
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte) 0;
        }
    }
}
