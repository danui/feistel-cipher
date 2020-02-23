package com.github.danui.feistel;

import static com.github.danui.feistel.FeistelUtils.xor;

public class FeistelStage {

    private final OneWayFunction oneway;

    public FeistelStage(final OneWayFunction oneway) {
        this.oneway = oneway;
    }

    public int blockSize() {
        return oneway.outputSize() * 2;
    }

    public SplitBytes apply(final SplitBytes input) {
        final byte[] otp = oneway.apply(input.getRight());
        final byte[] leftXorOtp = xor(input.getLeft(), otp);
        return new SplitBytes(input.getRight(), leftXorOtp);
    }
}
