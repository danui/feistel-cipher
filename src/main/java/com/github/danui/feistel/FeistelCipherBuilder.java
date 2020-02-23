package com.github.danui.feistel;

import com.google.common.base.Preconditions;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class FeistelCipherBuilder {
    private String keygenAlgo = "SHA1";
    private int keygenFolds = 16;
    private String onewayAlgo = "SHA1";
    private int onewayFolds = 16;
    private int blockSize = 64;
    private int numStages = 50;

    public FeistelCipherBuilder setKeygenAlgo(final String keygenAlgo) {
        this.keygenAlgo = keygenAlgo;
        return this;
    }

    public FeistelCipherBuilder setKeygenFolds(final int keygenFolds) {
        this.keygenFolds = keygenFolds;
        return this;
    }

    public FeistelCipherBuilder setOnewayAlgo(final String onewayAlgo) {
        this.onewayAlgo = onewayAlgo;
        return this;
    }

    public FeistelCipherBuilder setOnewayFolds(final int onewayFolds) {
        this.onewayFolds = onewayFolds;
        return this;
    }

    public FeistelCipherBuilder setBlockSize(final int blockSize) {
        Preconditions.checkArgument(blockSize >= 2, "block size must be at least 2");
        Preconditions.checkArgument(blockSize % 2 == 0, "block size must be divisible by 2");
        this.blockSize = blockSize;
        return this;
    }

    public FeistelCipherBuilder setNumStages(final int numStages) {
        Preconditions.checkArgument(numStages >= 2, "number of stages should be at least 2");
        this.numStages = numStages;
        return this;
    }

    public FeistelCipher build(final byte[] rootKey) {
        Preconditions.checkArgument(rootKey.length >= 1, "root key length must be at least 1");
        final OneWayFunction oneway = new MessageDigestOneWayFunction(onewayAlgo, onewayFolds, blockSize / 2);
        final ByteGenerator gen = new ByteGenerator(keygenAlgo, rootKey, keygenFolds);
        final List<FeistelStage> stages = new ArrayList<>();
        for (int i = 0; i < numStages; ++i) {
            final byte[] stageKey = new byte[rootKey.length];
            final OneWayFunction keyedOneway = new KeyedOneWayFunction(oneway, stageKey);
            final FeistelStage stage = new FeistelStage(keyedOneway);
            stages.add(stage);
        }
        return new FeistelCipher(stages);
    }
}
