package com.github.danui.feistel;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ParametersAreNonnullByDefault
public class FeistelCipher {
    private final int blockSize;
    private final List<FeistelStage> stages;
    private final List<FeistelStage> reversedStages;

    public FeistelCipher(final List<FeistelStage> stages) {
        Preconditions.checkArgument(stages.size() >= 2);
        this.blockSize = stages.get(0).blockSize();
        for (final FeistelStage stage : stages) {
            Preconditions.checkArgument(stage.blockSize() == blockSize);
        }
        this.stages = stages;
        this.reversedStages = new ArrayList<>(stages);
        Collections.reverse(reversedStages);
    }

    public int blockSize() {
        return blockSize;
    }

    @Nonnull
    public byte[] encrypt(final byte[] plaintext) {
        return exec(plaintext, stages);
    }

    @Nonnull
    public byte[] decrypt(final byte[] ciphertext) {
        return exec(ciphertext, reversedStages);
    }

    private byte[] exec(final byte[] input, final List<FeistelStage> stageOrder) {
        Preconditions.checkArgument(input.length == blockSize());
        SplitBytes splitBytes = SplitBytes.split(input);
        for (final FeistelStage stage : stageOrder) {
            splitBytes = stage.apply(splitBytes);
        }
        splitBytes.swap();
        return splitBytes.concat();
    }
}
