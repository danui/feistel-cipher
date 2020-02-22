package com.github.danui.feistel;

import com.google.common.primitives.Bytes;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class KeyedOneWayFunction implements OneWayFunction {
    private final OneWayFunction oneway;
    private final byte[] key;

    public KeyedOneWayFunction(final OneWayFunction oneway, final byte[] key) {
        this.oneway = oneway;
        this.key = key;
    }

    @Override
    @Nonnull
    public byte[] apply(final byte[] inputs) {
        final byte[] combined = Bytes.concat(inputs, key);
        return oneway.apply(combined);
    }
}
