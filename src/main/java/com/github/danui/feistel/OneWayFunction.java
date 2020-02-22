package com.github.danui.feistel;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

@ParametersAreNonnullByDefault
public interface OneWayFunction extends Function<byte[], byte[]> {

    @Override
    byte[] apply(final byte[] inputs);
}
