package com.github.danui.feistel;

import org.junit.Test;

import static com.github.danui.feistel.Helpers.prngBytes;
import static com.github.danui.feistel.Helpers.verifyDifferent;
import static com.github.danui.feistel.Helpers.verifySame;

public class KeyedOneWayFunctionTest {
    @Test
    public void shouldBeDeterministic() {
        final byte[] key = prngBytes(1024, 1L);
        final OneWayFunction fn = new KeyedOneWayFunction(new MessageDigestOneWayFunction("SHA1", 2, 32), key);
        final byte[] inputs = prngBytes(800, 2L);
        final byte[] out1 = fn.apply(inputs);
        final byte[] out2 = fn.apply(inputs);
        verifySame(out1, out2);
    }

    @Test
    public void shouldReturnDifferentOutputWhenKeyIsDifferent() {
        // Same one way function
        final OneWayFunction oneWayFunction = new MessageDigestOneWayFunction("SHA1", 2, 32);

        // Key 1
        final byte[] key1 = prngBytes(1024, 100L);
        key1[1023] = (byte) 0x00;
        final OneWayFunction fn1 = new KeyedOneWayFunction(oneWayFunction, key1);

        // Key 2
        final byte[] key2 = prngBytes(1024, 100L);
        key2[1023] = (byte) 0x01; // Just one bit different!
        final OneWayFunction fn2 = new KeyedOneWayFunction(oneWayFunction, key2);

        final byte[] inputs = prngBytes(1024, 8000L);
        final byte[] out1 = fn1.apply(inputs);
        final byte[] out2 = fn2.apply(inputs);

        verifyDifferent(out1, out2, 0.99);
    }
}
