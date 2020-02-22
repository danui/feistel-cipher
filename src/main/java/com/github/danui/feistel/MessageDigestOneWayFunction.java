package com.github.danui.feistel;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.github.danui.feistel.FeistelUtils.xor;
import static com.github.danui.feistel.FeistelUtils.zeroize;

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
            final DigestGenerator gen = new DigestGenerator(algo, inputs);
            final byte[] result = new byte[outputLength];
            final byte[] fold = new byte[outputLength];
            zeroize(result);
            for (int i = 0; i < folds; ++i) {
                gen.nextBytes(fold);
                for (int j = 0; j < outputLength; ++j) {
                    result[j] = xor(result[j], fold[j]);
                }
            }
            return result;
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
