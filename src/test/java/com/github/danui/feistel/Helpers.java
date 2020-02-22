package com.github.danui.feistel;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@ParametersAreNonnullByDefault
public class Helpers {

    @Nonnull
    public static byte[] prngBytes(final int length, final long seed) {
        final Random prng = new Random(seed);
        final byte[] result = new byte[length];
        prng.nextBytes(result);
        return result;
    }

    public static void verifySame(final byte[] x, final byte[] y) {
        assertEquals(x.length, y.length);
        for (int i = 0; i < x.length; ++i) {
            assertEquals(x[i], y[i]);
        }
    }

    public static void verifyDifferent(final byte[] x, final byte[] y) {
        if (x.length != y.length) {
            return;
        }
        for (int i = 0; i < x.length; ++i) {
            if (x[i] != y[i]) {
                return;
            }
        }
        fail("both arrays are equal");
    }

    public static void verifyDifferent(final byte[] x, final byte[] y, final double requiredScore) {
        int count = 0;
        final int minLength = Math.min(x.length, y.length);
        final int maxLength = Math.max(x.length, y.length);
        for (int i = 0; i < minLength; ++i) {
            if (x[i] != y[i]) {
                count += 1;
            }
        }
        final double score = ((double) count) / ((double) maxLength);
        if (score < requiredScore) {
            fail(String.format("Score of %f is less than required score %f", score, requiredScore));
        }
    }
}
