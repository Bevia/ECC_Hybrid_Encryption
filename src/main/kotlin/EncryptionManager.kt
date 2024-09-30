package org.example

import javax.crypto.SecretKey

interface EncryptionManager {
    fun encrypt(data: ByteArray, key: SecretKey): Pair<String, ByteArray>
    fun decrypt(cipherText: String, key: SecretKey, iv: ByteArray): String
}