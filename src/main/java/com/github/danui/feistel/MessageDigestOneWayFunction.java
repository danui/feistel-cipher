package com.github.danui.feistel;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MessageDigestOneWayFunction implements OneWayFunction {

    private final String algo;
    private final int folds;
    private final int outputLength;

    public MessageDigestOneWayFunction(final String algo, final int folds, final int outputLength) {
        this.algo = algo;
        this.folds = folds;
        this.outputLength = outputLength;
    }

    @Override
    @Nonnull
    public byte[] apply(final byte[] inputs) {
        try {
            final ByteGenerator gen = new ByteGenerator(algo, inputs, folds);
            final byte[] result = new byte[outputLength];
            gen.nextBytes(result);
            return result;
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
