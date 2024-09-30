package org.example

import java.security.KeyPair

interface KeyPairGeneratorManager {
    fun generateKeyPair(): KeyPair
}
