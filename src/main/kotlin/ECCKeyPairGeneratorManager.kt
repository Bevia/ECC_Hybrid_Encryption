package org.example

import java.security.KeyPair
import java.security.KeyPairGenerator

class ECCKeyPairGeneratorManager : KeyPairGeneratorManager {

    override fun generateKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC")
        keyPairGenerator.initialize(256)
        return keyPairGenerator.generateKeyPair()
    }
}