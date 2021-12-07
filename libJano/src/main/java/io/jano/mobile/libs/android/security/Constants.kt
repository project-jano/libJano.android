package io.jano.mobile.libs.android.security

import android.security.keystore.KeyProperties

internal class Constants {
    companion object {
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_ECB
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1

        private const val KEY_SIZE_IN_BYTES = 512

        const val KEY_SIZE_IN_BITS = KEY_SIZE_IN_BYTES * 8

        const val PLATFORM = "mobile"

        const val ANDROID_KEYSTORE_PROVIDER_NAME = "AndroidKeyStore"

        const val SIGNATURE_ALGORITHM = "SHA512"
        const val RSA_SIGNATURE_ALGORITHM = "SHA512withRSA"

        const val CSR_BEGIN = "-----BEGIN CERTIFICATE REQUEST-----"
        const val CSR_END = "-----END CERTIFICATE REQUEST-----"
        const val CERT_BEGIN = "-----BEGIN CERTIFICATE-----"
        const val CERT_END = "-----END CERTIFICATE-----"

        const val KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_RSA

        const val CIPHER_TRANSFORMATION = "${KEY_ALGORITHM}/${BLOCK_MODE}/${PADDING}"
    }
}