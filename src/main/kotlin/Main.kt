package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import java.util.Base64
import javax.crypto.spec.GCMParameterSpec

// ECC KeyPair Generation
fun generateECCKeyPair(): KeyPair {
    val keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC")
    keyPairGenerator.initialize(256)
    return keyPairGenerator.generateKeyPair()
}

// AES Key Generation
fun generateAESKey(): SecretKey {
    val keyGenerator = KeyGenerator.getInstance("AES")
    keyGenerator.init(256)  // AES 256-bit key
    return keyGenerator.generateKey()
}

// AES Encryption
fun encryptWithAES(plainText: String, aesKey: SecretKey): Pair<String, ByteArray> {
    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    val iv = ByteArray(12) // GCM needs a 12-byte IV
    SecureRandom().nextBytes(iv)
    cipher.init(Cipher.ENCRYPT_MODE, aesKey, GCMParameterSpec(128, iv))
    val cipherText = cipher.doFinal(plainText.toByteArray())
    return Pair(Base64.getEncoder().encodeToString(cipherText), iv)
}

// AES Decryption
fun decryptWithAES(cipherText: String, aesKey: SecretKey, iv: ByteArray): String {
    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, aesKey, GCMParameterSpec(128, iv))
    val plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText))
    return String(plainText)
}

// ECC Encryption of AES Key
fun encryptAESKeyWithECC(aesKey: SecretKey, publicKey: PublicKey): String {
    val cipher = Cipher.getInstance("ECIES", "BC")
    cipher.init(Cipher.ENCRYPT_MODE, publicKey)
    val encryptedAESKey = cipher.doFinal(aesKey.encoded)
    return Base64.getEncoder().encodeToString(encryptedAESKey)
}

// ECC Decryption of AES Key
fun decryptAESKeyWithECC(encryptedAESKey: String, privateKey: PrivateKey): SecretKey {
    val cipher = Cipher.getInstance("ECIES", "BC")
    cipher.init(Cipher.DECRYPT_MODE, privateKey)
    val decodedAESKey = cipher.doFinal(Base64.getDecoder().decode(encryptedAESKey))
    return SecretKeySpec(decodedAESKey, 0, decodedAESKey.size, "AES")
}

fun main() {
    // Register BouncyCastle as the provider
    Security.addProvider(BouncyCastleProvider())

    // Step 1: Generate ECC KeyPair (Public and Private Key)
    val eccKeyPair = generateECCKeyPair()

    // Step 2: Generate AES Key
    val aesKey = generateAESKey()

    // Step 3: Encrypt the message using AES
    val message = "this is my secret message"
    val (encryptedMessage, iv) = encryptWithAES(message, aesKey)
    println("Encrypted message: $encryptedMessage")

    // Step 4: Encrypt the AES key using ECC public key
    val encryptedAESKey = encryptAESKeyWithECC(aesKey, eccKeyPair.public)
    println("Encrypted AES key: $encryptedAESKey")

    // Step 5: Decrypt the AES key using ECC private key
    val decryptedAESKey = decryptAESKeyWithECC(encryptedAESKey, eccKeyPair.private)

    // Step 6: Decrypt the message using the decrypted AES key
    val decryptedMessage = decryptWithAES(encryptedMessage, decryptedAESKey, iv)
    println("Decrypted message: $decryptedMessage")
}