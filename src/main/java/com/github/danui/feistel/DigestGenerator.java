package com.github.danui.feistel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class DigestGenerator {

    private final MessageDigest md;
    private byte[] buf;
    private int idx;

    DigestGenerator(final String algo, final byte[] seed) {
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
        if (buf == null || idx == buf.length) {
            buf = md.digest();
            idx = buf.length / 2;
            md.update(buf, 0, idx);
        }
        return buf[idx++];
    }
}
