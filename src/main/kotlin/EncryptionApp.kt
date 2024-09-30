package org.example

import javax.crypto.KeyGenerator


class EncryptionApp(
    private val aesEncryptionManager: EncryptionManager,
    private val eccKeyManager: ECCKeyManager,
    private val keyPairGenerator: KeyPairGeneratorManager
) {

    fun runEncryptionDemo(message: String) {
        // Step 1: Generate ECC KeyPair
        val eccKeyPair = keyPairGenerator.generateKeyPair()

        // Step 2: Generate AES Key
        val aesKey = KeyGenerator.getInstance("AES").apply { init(256) }.generateKey()

        // Step 3: Encrypt the message using AES
        val (encryptedMessage, iv) = aesEncryptionManager.encrypt(message.toByteArray(), aesKey)
        println("Encrypted message: $encryptedMessage")

        // Step 4: Encrypt the AES key using ECC public key
        val encryptedAESKey = eccKeyManager.encryptKey(aesKey, eccKeyPair.public)
        println("Encrypted AES key: $encryptedAESKey")

        // Step 5: Decrypt the AES key using ECC private key
        val decryptedAESKey = eccKeyManager.decryptKey(encryptedAESKey, eccKeyPair.private)

        // Step 6: Decrypt the message using the decrypted AES key
        val decryptedMessage = aesEncryptionManager.decrypt(encryptedMessage, decryptedAESKey, iv)
        println("Decrypted message: $decryptedMessage")
    }
}