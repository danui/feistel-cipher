package com.github.danui.feistel;

import java.nio.ByteBuffer;

class IntCounter {
    private final byte[] bytes;
    private final ByteBuffer wrapper;
    private int value;

    IntCounter() {
        this.value = 0;
        this.bytes = new byte[4];
        this.wrapper = ByteBuffer.wrap(this.bytes);
    }

    byte[] bytes() {
        update();
        return bytes;
    }

    void increment() {
        value += 1;
    }

    private void update() {
        wrapper.rewind();
        wrapper.putInt(value);
    }
}
