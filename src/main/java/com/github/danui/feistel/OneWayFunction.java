package com.github.danui.feistel;

public interface OneWayFunction {
    /**
     * @param inputs       Input bytes
     * @param outputLength Length of the output
     * @return Byte array of the specified length
     */
    byte[] apply(final byte[] inputs, final int outputLength);
}
