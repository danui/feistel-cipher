package com.github.danui.feistel;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KeyedOneWayFunctionTest {
    @Test
    public void shouldBeDeterministic() {
        final byte[] key = filled(1024);
        final OneWayFunction fn = new KeyedOneWayFunction(new MessageDigestOneWayFunction("SHA1", 2, 32), key);
        final byte[] inputs = filled(800);
        final byte[] out1 = fn.apply(inputs);
        final byte[] out2 = fn.apply(inputs);
        for (int i = 0; i < 32; ++i) {
            assertEquals(out1[i], out2[i]);
        }
    }

    @Test
    public void shouldReturnDifferentOutputWhenKeyIsDifferent() {
        // Same one way function
        final OneWayFunction oneWayFunction = new MessageDigestOneWayFunction("SHA1", 2, 32);

        // Key 1
        final byte[] key1 = filled(1024, 100L);
        key1[1023] = (byte) 0x00;
        final OneWayFunction fn1 = new KeyedOneWayFunction(oneWayFunction, key1);

        // Key 2
        final byte[] key2 = filled(1024, 100L);
        key2[1023] = (byte) 0x01; // Just one bit different!
        final OneWayFunction fn2 = new KeyedOneWayFunction(oneWayFunction, key2);

        final byte[] inputs = filled(1024, 8000L);
        final byte[] out1 = fn1.apply(inputs);
        final byte[] out2 = fn2.apply(inputs);

        int numberOfDifferences = 0;
        for (int i = 0; i < out1.length; ++i) {
            if (out1[i] != out2[i]) {
                numberOfDifferences += 1;
            }
        }
        assertTrue((double) numberOfDifferences >= 0.99 * out1.length);
    }

    private byte[] filled(final int length) {
        return filled(length, 0L);
    }

    private byte[] filled(final int length, final long seed) {
        final Random prng = new Random(seed);
        final byte[] result = new byte[length];
        prng.nextBytes(result);
        return result;
    }
}
