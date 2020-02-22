package com.github.danui.feistel;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FeistelCipher {
    private final int blockSize;
    private final byte[] rootKey;
    private final int numStages;

    public FeistelCipher(final int blockSize, final byte[] rootKey, final int numStages) {
        Preconditions.checkArgument(blockSize >= 2, "block size must be at least 2");
        Preconditions.checkArgument(blockSize % 2 == 0, "block size must be divisible by 2");
        Preconditions.checkArgument(rootKey.length >= 1, "root key length must be at least 1");
        Preconditions.checkArgument(numStages >= 2, "number of stages should be at least 2");
        this.blockSize = blockSize;
        this.rootKey = rootKey;
        this.numStages = numStages;
    }

    @Nonnull
    public byte[] encrypt(final byte[] plaintext) {
        Preconditions.checkArgument(plaintext.length == blockSize);
        // TODO.
        return plaintext;
    }

    @Nonnull
    public byte[] decrypt(final byte[] ciphertext) {
        Preconditions.checkArgument(ciphertext.length == blockSize);
        return ciphertext;
    }
}
