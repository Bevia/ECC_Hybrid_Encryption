package org.example

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security

/*
    How SOLID is Applied:

	1.	Single Responsibility Principle (SRP):
	•	Each class has a single responsibility:
	•	AESEncryptionManager: Handles AES encryption and decryption.
	•	ECCKeyManagerImpl: Handles ECC encryption and decryption of the AES key.
	•	ECCKeyPairGeneratorManager: Manages ECC key pair generation.
	•	The EncryptionApp class coordinates these services without implementing them directly.
	2.	Open/Closed Principle (OCP):
	•	If you want to extend the encryption mechanisms, you can easily create new classes (e.g., RSAEncryptionManager),
	without modifying the existing classes. For example, you could introduce another encryption manager without changing the EncryptionApp.
	3.	Liskov Substitution Principle (LSP):
	•	You can replace the EncryptionManager or ECCKeyManager implementations with any other algorithm that implements
	the respective interfaces, without breaking the code.
	4.	Interface Segregation Principle (ISP):
	•	Each interface (EncryptionManager, ECCKeyManager, etc.) is focused on a specific task. The classes implementing
	these interfaces are not forced to depend on methods they don’t need.
	5.	Dependency Inversion Principle (DIP):
	•	The EncryptionApp class depends on the abstractions (EncryptionManager, ECCKeyManager, KeyPairGeneratorManager)
	rather than specific implementations. This allows for easier testing and flexibility in extending the system in the future.

    Conclusion:

    This refactored code now follows the SOLID principles, making it more flexible, maintainable, and scalable.
    You can easily add new cryptographic algorithms or modify the existing logic without affecting the overall structure.
 */

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