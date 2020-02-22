package com.github.danui.feistel;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

public class MessageDigestOneWayFunction implements OneWayFunction {

    private final String algo;
    private final int folds;

    public MessageDigestOneWayFunction(final String algo, final int folds) {
        this.algo = algo;
        this.folds = folds;
    }

    @Override
    public byte[] apply(final byte[] inputs, final int outputLength) {
        try {
            byte[] churnBytes = inputs;
            final IntCounter counter = new IntCounter();
            final MessageDigest md = MessageDigest.getInstance(algo);
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            while (out.size() < outputLength * folds) {
                md.update(churnBytes);
                md.update(counter.bytes());
                churnBytes = md.digest();
                out.write(churnBytes);
                counter.increment();
            }
            final byte[] digested = out.toByteArray();
            final byte[] result = new byte[outputLength];
            zeroize(result);
            for (int i = 0; i < folds; ++i) {
                int base = i * outputLength;
                for (int j = 0; j < outputLength; ++j) {
                    result[j] = xor(result[j], digested[base + j]);
                }
            }
            return result;
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void zeroize(final byte[] bytes) {
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte) 0;
        }
    }

    private byte xor(final byte x, final byte y) {
        return (byte) (x ^ y);
    }
}
