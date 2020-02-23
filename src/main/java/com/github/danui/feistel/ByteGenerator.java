package com.github.danui.feistel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class ByteGenerator {

    private final int folds;
    private final MessageDigest md;
    private byte[] buf;
    private int idx;

    ByteGenerator(final String algo, final byte[] seed, int folds) {
        this.folds = folds;
        try {
            this.md = MessageDigest.getInstance(algo);
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        md.update(seed);
    }

    void nextBytes(final byte[] output) {
        for (int i = 0; i < output.length; ++i) {
            output[i] = nextByte();
        }
    }

    private byte nextByte() {
        byte b = 0;
        for (int i = 0; i < folds; ++i) {
            b = (byte) (b ^ nextRawByte());
        }
        return b;
    }

    private byte nextRawByte() {
        if (buf == null || idx == buf.length) {
            buf = md.digest();
            idx = buf.length / 2;
            md.update(buf, 0, idx);
        }
        return buf[idx++];
    }
}
