package com.github.danui.feistel;

import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.github.danui.feistel.FeistelUtils.xor;
import static com.github.danui.feistel.Helpers.prngBytes;
import static com.github.danui.feistel.Helpers.verifySame;
import static org.junit.Assert.assertEquals;

@ParametersAreNonnullByDefault
public class FeistelStageTest {
    @Test
    public void testFeistelStage() {
        final OneWayFunction oneway = defaultOneWayFunction(8);
        final byte[] left = prngBytes(8, 1L);
        final byte[] right = prngBytes(8, 2L);
        final byte[] otp = oneway.apply(right);
        final byte[] leftXorOtp = xor(left, otp);
        final SplitBytes stageInput = new SplitBytes(left, right);
        final FeistelStage stage = new FeistelStage(oneway);
        assertEquals(16, stage.blockSize());
        final SplitBytes stageOutput = stage.apply(stageInput);
        verifySame(leftXorOtp, stageOutput.getRight());
        verifySame(right, stageOutput.getLeft());
    }

    private OneWayFunction defaultOneWayFunction(final int outputSize) {
        return new OneWayFunction() {
            @Override
            public int outputSize() {
                return outputSize;
            }

            @Nonnull
            @Override
            public byte[] apply(final byte[] inputs) {
                final byte[] outputs = new byte[outputSize()];
                for (int i = 0; i < outputSize(); ++i) {
                    outputs[i] = inputs[i % inputs.length];
                }
                return outputs;
            }
        };
    }
}