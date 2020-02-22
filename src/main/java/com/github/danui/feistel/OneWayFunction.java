package com.github.danui.feistel;

public interface OneWayFunction {
    byte[] apply(final byte[] inputs);
}
