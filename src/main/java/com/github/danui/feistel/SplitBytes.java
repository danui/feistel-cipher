package com.github.danui.feistel;

import com.google.common.primitives.Bytes;

class SplitBytes {
    private byte[] left;
    private byte[] right;

    SplitBytes(final byte[] left, final byte[] right) {
        this.left = left;
        this.right = right;
    }

    static SplitBytes split(final byte[] block) {
        final int halfSize = block.length / 2;
        final byte[] left = new byte[halfSize];
        final byte[] right = new byte[halfSize];
        System.arraycopy(block, 0, left, 0, halfSize);
        System.arraycopy(block, halfSize, right, 0, halfSize);
        return new SplitBytes(left, right);
    }

    byte[] getLeft() {
        return left;
    }

    byte[] getRight() {
        return right;
    }

    void swap() {
        final byte[] tmp = left;
        left = right;
        right = tmp;
    }

    byte[] concat() {
        return Bytes.concat(left, right);
    }
}
