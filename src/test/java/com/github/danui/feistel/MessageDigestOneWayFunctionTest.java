package com.github.danui.feistel;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageDigestOneWayFunctionTest {
    @Test
    public void shouldReturnTheRequestedNumberOfBytes() {
        final OneWayFunction fn = new MessageDigestOneWayFunction("SHA1", 2, 32);
        final byte[] inputs = filled(1000);
        final byte[] results = fn.apply(inputs);
        assertEquals(32, results.length);
    }

    @Test
    public void shouldBeDeterministic() {
        final OneWayFunction fn = new MessageDigestOneWayFunction("SHA1", 2, 32);
        final byte[] inputs = filled(800);
        final byte[] out1 = fn.apply(inputs);
        final byte[] out2 = fn.apply(inputs);
        for (int i = 0; i < 32; ++i) {
            assertEquals(out1[i], out2[i]);
        }
    }

    @Test
    public void exampleOutput() {
        final OneWayFunction fn = new MessageDigestOneWayFunction("SHA1", 100, 8);
        final byte[] inputs = filled(100);
        final byte[] out = fn.apply(inputs);
        assertEquals("5f843fad3e3d34f2", Hex.encodeHexString(out));
    }

    private byte[] filled(final int length) {
        final byte[] result = new byte[length];
        for (int i = 0; i < length; ++i) {
            result[i] = (byte) (i & 0xff);
        }
        return result;
    }
}