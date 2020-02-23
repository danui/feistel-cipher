package com.github.danui.feistel;

import org.junit.Test;

import static com.github.danui.feistel.Helpers.prngBytes;
import static com.github.danui.feistel.Helpers.verifyDifferent;
import static com.github.danui.feistel.Helpers.verifySame;

public class FeistelCipherTest {

    private static final int BLOCK_SIZE = 256;
    private static final byte[] KEY = prngBytes(2048, 168L);
    private static final FeistelCipher CIPHER = new FeistelCipherBuilder()
            .setKeygenAlgo("SHA-256")
            .setKeygenFolds(888)
            .setOnewayAlgo("SHA-256")
            .setOnewayFolds(64)
            .setNumStages(84)
            .setBlockSize(BLOCK_SIZE)
            .build(KEY);

    @Test
    public void encryptionShouldBeStable() {
        final byte[] inputs = prngBytes(BLOCK_SIZE, 1L);
        verifySame(CIPHER.encrypt(inputs), CIPHER.encrypt(inputs));
    }

    @Test
    public void decryptionShouldReverseEncryption() {
        final byte[] inputs = prngBytes(BLOCK_SIZE, 2L);
        final byte[] ciphertext = CIPHER.encrypt(inputs);
        final byte[] decrypted = CIPHER.decrypt(ciphertext);
        verifySame(inputs, decrypted);
    }

    @Test
    public void ciphertextShouldBeDifferentFromInput() {
        final byte[] inputs = prngBytes(BLOCK_SIZE, 3L);
        final byte[] ciphertext = CIPHER.encrypt(inputs);
        verifyDifferent(inputs, ciphertext, .98);
    }
}
