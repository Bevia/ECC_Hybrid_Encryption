package org.example

import java.security.PrivateKey
import java.security.PublicKey
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class ECCKeyManagerImpl : ECCKeyManager {

    override fun encryptKey(key: SecretKey, publicKey: PublicKey): String {
        val cipher = Cipher.getInstance("ECIES", "BC")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptedAESKey = cipher.doFinal(key.encoded)
        return Base64.getEncoder().encodeToString(encryptedAESKey)
    }

    override fun decryptKey(encryptedKey: String, privateKey: PrivateKey): SecretKey {
        val cipher = Cipher.getInstance("ECIES", "BC")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val decodedAESKey = cipher.doFinal(Base64.getDecoder().decode(encryptedKey))
        return SecretKeySpec(decodedAESKey, 0, decodedAESKey.size, "AES")
    }
}
