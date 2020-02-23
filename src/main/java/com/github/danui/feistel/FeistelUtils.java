package com.github.danui.feistel;

public class FeistelUtils {
    private FeistelUtils() {
    }

    static byte[] xor(final byte[] x, final byte[] y) {
        final byte[] result = new byte[x.length];
        for (int i = 0; i < x.length; ++i) {
            result[i] = (byte) (x[i] ^ y[i]);
        }
        return result;
    }
}
