package org.example

import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.SecretKey

interface ECCKeyManager {
    fun encryptKey(key: SecretKey, publicKey: PublicKey): String
    fun decryptKey(encryptedKey: String, privateKey: PrivateKey): SecretKey
}
