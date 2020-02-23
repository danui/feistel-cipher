package com.github.danui.feistel;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import static com.github.danui.feistel.Helpers.prngBytes;
import static com.github.danui.feistel.Helpers.verifySame;
import static org.junit.Assert.assertEquals;

public class MessageDigestOneWayFunctionTest {
    @Test
    public void shouldReturnTheRequestedNumberOfBytes() {
        final OneWayFunction fn = new MessageDigestOneWayFunction("SHA1", 2, 32);
        final byte[] inputs = prngBytes(1000, 10L);
        final byte[] results = fn.apply(inputs);
        assertEquals(32, results.length);
    }

    @Test
    public void shouldBeDeterministic() {
        final OneWayFunction fn = new MessageDigestOneWayFunction("SHA1", 2, 32);
        final byte[] inputs = prngBytes(800, 10L);
        final byte[] out1 = fn.apply(inputs);
        final byte[] out2 = fn.apply(inputs);
        verifySame(out1, out2);
    }

    @Test
    public void exampleOutput() {
        final OneWayFunction fn = new MessageDigestOneWayFunction("SHA1", 100, 8);
        final byte[] inputs = prngBytes(100, 10L);
        final byte[] out = fn.apply(inputs);
        assertEquals("ef9809e010afa453", Hex.encodeHexString(out));
    }
}