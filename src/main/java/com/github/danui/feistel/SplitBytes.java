package com.github.danui.feistel;

import com.google.common.primitives.Bytes;

class SplitBytes {
    private byte[] left;
    private byte[] right;

    SplitBytes(final byte[] left, final byte[] right) {
        this.left = left;
        this.right = right;
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
