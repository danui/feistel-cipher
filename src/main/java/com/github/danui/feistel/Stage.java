package com.github.danui.feistel;

import com.google.common.primitives.Bytes;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

@ParametersAreNonnullByDefault
public class Stage implements Function<byte[], byte[]> {
    private final OneWayFunction oneway;
    private final byte[] key;

    public Stage(final OneWayFunction oneway, final byte[] key) {
        this.oneway = oneway;
        this.key = key;
    }

    @Override
    public byte[] apply(final byte[] inputs) {
        final byte[] combined = Bytes.concat(inputs, key);
        return oneway.apply(combined, inputs.length);
    }
}
