package com.github.danui.feistel;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.github.danui.feistel.FeistelUtils.xor;

@ParametersAreNonnullByDefault
public class FeistelCipher {
    private final int blockSize;
    private final List<Function<SplitBytes, SplitBytes>> stages;
    private final List<Function<SplitBytes, SplitBytes>> reversedStages;

    public FeistelCipher(final int blockSize, final byte[] rootKey, final int numStages) {
        Preconditions.checkArgument(blockSize >= 2, "block size must be at least 2");
        Preconditions.checkArgument(blockSize % 2 == 0, "block size must be divisible by 2");
        Preconditions.checkArgument(rootKey.length >= 1, "root key length must be at least 1");
        Preconditions.checkArgument(numStages >= 2, "number of stages should be at least 2");
        this.blockSize = blockSize;
        this.stages = new ArrayList<>();
        final ByteGenerator gen = new ByteGenerator("SHA1", rootKey, 8); // TODO: Key expansion algo should be configurable.
        final OneWayFunction oneway = new MessageDigestOneWayFunction("SHA1", 4, blockSize / 2); // TODO: This function should be an input.
        for (int i = 0; i < numStages; ++i) {
            final byte[] stageKey = new byte[rootKey.length];
            gen.nextBytes(stageKey);
            final OneWayFunction keyedOneway = new KeyedOneWayFunction(oneway, stageKey);
            stages.add(makeStage(keyedOneway));
        }
        this.reversedStages = new ArrayList<>(stages);
        Collections.reverse(reversedStages);
    }

    @Nonnull
    public byte[] encrypt(final byte[] plaintext) {
        return exec(plaintext, stages);
    }

    @Nonnull
    public byte[] decrypt(final byte[] ciphertext) {
        return exec(ciphertext, reversedStages);
    }

    private byte[] exec(final byte[] input, final List<Function<SplitBytes, SplitBytes>> stageOrder) {
        Preconditions.checkArgument(input.length == blockSize);
        SplitBytes splitBytes = SplitBytes.split(input);
        for (final Function<SplitBytes, SplitBytes> stage : stageOrder) {
            splitBytes = stage.apply(splitBytes);
        }
        splitBytes.swap();
        return splitBytes.concat();
    }

    private Function<SplitBytes, SplitBytes> makeStage(final OneWayFunction oneway) {
        return (input) -> {
            final byte[] otp = oneway.apply(input.getRight());
            final byte[] xorOutput = xor(input.getLeft(), otp);
            return new SplitBytes(input.getRight(), xorOutput);
        };
    }
}
