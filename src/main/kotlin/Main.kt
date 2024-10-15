package org.example

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security

fun main() {
    // Register BouncyCastle as the provider
    Security.addProvider(BouncyCastleProvider())

    // Setup the app with dependency injection
    val aesEncryptionManager = AESEncryptionManager()
    val eccKeyManager = ECCKeyManagerImpl()
    val eccKeyPairGenerator = ECCKeyPairGeneratorManager()

    val app = EncryptionApp(aesEncryptionManager, eccKeyManager, eccKeyPairGenerator)
    app.runEncryptionDemo("this is my secret message")
}