package io.jano.mobile.libs.android.security

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import io.jano.mobile.libs.android.SecurityOptions
import io.jano.mobile.libs.android.exceptions.InvalidKeyStoreEntry
import io.jano.mobile.libs.android.exceptions.InvalidSignatureException
import io.jano.mobile.libs.android.exceptions.KeyStoreEntryNotFound
import io.jano.mobile.libs.android.exceptions.MissingRootCertificateException
import io.jano.mobile.libs.android.models.Device
import io.jano.mobile.libs.android.models.Payload
import io.jano.mobile.libs.android.models.SecuredPayload
import org.spongycastle.jce.provider.BouncyCastleProvider
import org.spongycastle.operator.jcajce.JcaContentSignerBuilder
import org.spongycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder
import java.math.BigInteger
import java.security.*
import java.security.cert.X509Certificate
import java.util.*
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal


internal class SecurityManager {

    companion object {

        private val standardKeyStore =
            KeyStore.getInstance(Constants.ANDROID_KEYSTORE_PROVIDER_NAME)

        init {
            standardKeyStore.load(null)
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                Security.addProvider(BouncyCastleProvider())
            }
        }

        fun hasCertificates(
            userId: String,
            deviceId: String,
            alias: String,
        ): Boolean {
            return try {
                val entryAlias = entryAlias(userId, deviceId, alias)
                return standardKeyStore.containsAlias(entryAlias) &&
                        standardKeyStore.isKeyEntry(entryAlias)
            } catch (e: Exception) {
                false
            }
        }

        fun generateCertificate(
            context: Context,
            userId: String,
            deviceId: String,
            alias: String,
            subject: String,
            notBefore: Date,
            notAfter: Date,
            options: SecurityOptions
        ): PublicKey {

            val kpg: KeyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA, Constants.ANDROID_KEYSTORE_PROVIDER_NAME
            )

            val entryAlias = entryAlias(userId = userId, deviceId = deviceId, alias = alias)

            val parameterSpec = KeyGenParameterSpec.Builder(
                entryAlias,
                KeyProperties.PURPOSE_SIGN or
                        KeyProperties.PURPOSE_VERIFY or
                        KeyProperties.PURPOSE_DECRYPT or
                        KeyProperties.PURPOSE_ENCRYPT
            ).run {

                val serialNumber = BigInteger(64, SecureRandom())

                val issuer = context.applicationInfo.loadLabel(context.packageManager)

                val subjectPrincipal =
                    X500Principal("CN=$subject, UID=$userId, O=${issuer}, OU=${context.packageName}, C=${Locale.getDefault().country}")

                setCertificateSubject(subjectPrincipal)
                setCertificateNotBefore(notBefore)
                setCertificateNotAfter(notAfter)
                setCertificateSerialNumber(serialNumber)

                setDigests(
                    KeyProperties.DIGEST_SHA512,
                    KeyProperties.DIGEST_SHA384,
                    KeyProperties.DIGEST_SHA256
                )

                setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)

                setKeySize(Constants.KEY_SIZE_IN_BITS)
                setRandomizedEncryptionRequired(false)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    setIsStrongBoxBacked(options.strongBoxBacked)
                    setUserPresenceRequired(options.userPresenceRequired)
                    setUnlockedDeviceRequired(options.unlockedDeviceRequired)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setInvalidatedByBiometricEnrollment(options.invalidatedByBiometricEnrollment)
                }
                setUserAuthenticationRequired(options.userAuthenticationRequired)
                build()
            }

            kpg.initialize(parameterSpec)
            val keyPair = kpg.generateKeyPair()
            return keyPair.public
        }

        fun getCertificates(
            userId: String,
            deviceId: String,
            alias: String,
        ): List<X509Certificate> {
            return try {
                val privateKeyEntry = loadPrivateKeyEntry(userId, deviceId, alias)
                privateKeyEntry.certificateChain.map { it as X509Certificate }
            } catch (e: Exception) {
                listOf()
            }
        }

        fun createCertificateSigningRequest(
            userId: String,
            deviceId: String,
            alias: String,
        ): String {
            val privateKeyEntry = loadPrivateKeyEntry(userId, deviceId, alias)

            val privateKey = privateKeyEntry.privateKey
            val certificate = privateKeyEntry.certificate as X509Certificate

            return "${Constants.CSR_BEGIN}\n${
                Base64.encodeToString(
                    JcaPKCS10CertificationRequestBuilder(
                        certificate.subjectX500Principal, certificate.publicKey
                    )
                        .build(
                            JcaContentSignerBuilder(Constants.RSA_SIGNATURE_ALGORITHM)
                                .setSecureRandom(SecureRandom())
                                .build(privateKey)
                        )
                        .encoded,
                    Base64.DEFAULT
                )
            }${Constants.CSR_END}"
        }

        fun setSignedCertificate(
            userId: String,
            deviceId: String,
            alias: String,
            signedCertificateChain: List<X509Certificate>
        ) {
            val privateKey = loadPrivateKeyEntry(userId, deviceId, alias).privateKey
            standardKeyStore.setKeyEntry(
                entryAlias(userId, deviceId, alias),
                privateKey,
                null,
                signedCertificateChain.toTypedArray()
            )
        }

        fun encrypt(
            context: Context,
            userId: String,
            deviceId: String,
            alias: String,
            message: String,
            useServerCertificate: Boolean = true
        ): SecuredPayload {
            // Load private key
            val privateKeyEntry = loadPrivateKeyEntry(
                userId, deviceId, alias
            )

            // Decide if using SelfSigned (local) or Service (remote) certificate
            val certificate =
                getCertificateFromPrivateKey(useServerCertificate, privateKeyEntry)

            // Encrypt message
            val cipher = Cipher.getInstance(
                Constants.CIPHER_TRANSFORMATION,
            )

            cipher.init(Cipher.ENCRYPT_MODE, certificate.publicKey, secureRandom())

            val jsonString = Payload(
                timestamp = System.currentTimeMillis(),
                message = message,
                fingerprint = Device.fromLocalDevice(context).userAgent,
            ).toJSONString()
            val cipheredText = cipher.doFinal(jsonString.toByteArray())

            // Sign encrypted message
            val signer = getSignature(Constants.SIGNATURE_ALGORITHM)
            signer.initSign(privateKeyEntry.privateKey)
            signer.update(cipheredText)
            val signature = signer.sign()

            // return Secure Payload
            return SecuredPayload(
                keyId = alias,
                deviceId = deviceId,
                payload = Base64.encodeToString(cipheredText, Base64.NO_WRAP),
                signature = Base64.encodeToString(signature, Base64.NO_WRAP),
            )
        }

        fun decrypt(
            userId: String,
            deviceId: String,
            alias: String,
            securedPayload: String,
            signature: String,
            useServerCertificate: Boolean = true
        ): Payload {
            val privateKeyEntry = loadPrivateKeyEntry(userId, deviceId, alias)
            val certificates = privateKeyEntry.certificateChain
            val privateKey = privateKeyEntry.privateKey


            if (useServerCertificate && certificates.size < 2) throw MissingRootCertificateException(
                userId,
                deviceId,
                alias
            )

            // Decide if using SelfSigned (local) or Service (remote) certificate
            val certificate = getCertificateFromPrivateKey(useServerCertificate, privateKeyEntry)

            if (!isSignatureValid(
                    cipheredText = securedPayload,
                    signature = signature,
                    signatureAlgorithm = Constants.SIGNATURE_ALGORITHM,
                    rootCertificate = certificate,
                )
            ) {
                throw InvalidSignatureException()
            }

            val cipher = Cipher.getInstance(
                Constants.CIPHER_TRANSFORMATION,
            )

            cipher.init(Cipher.DECRYPT_MODE, privateKey, secureRandom())

            val plainTextBytes: ByteArray =
                cipher.doFinal(Base64.decode(securedPayload, Base64.NO_WRAP))

            return Payload.from(String(plainTextBytes))
        }

        private fun getCertificateFromPrivateKey(
            useServerCertificate: Boolean,
            privateKeyEntry: KeyStore.PrivateKeyEntry
        ): X509Certificate {
            val certificate =
                if (useServerCertificate) privateKeyEntry.certificateChain.last() else {
                    privateKeyEntry.certificateChain.first()
                } as X509Certificate
            return certificate
        }

        private fun isSignatureValid(
            cipheredText: String,
            signature: String,
            signatureAlgorithm: String,
            rootCertificate: X509Certificate,
        ): Boolean {
            val s = getSignature(signatureAlgorithm)
            s.initVerify(rootCertificate)
            s.update(Base64.decode(cipheredText, Base64.DEFAULT))
            return s.verify(Base64.decode(signature, Base64.DEFAULT))
        }

        @Throws(KeyStoreEntryNotFound::class, InvalidKeyStoreEntry::class, KeyStoreException::class)
        private fun loadPrivateKeyEntry(
            userId: String,
            deviceId: String,
            alias: String,
        ): KeyStore.PrivateKeyEntry {

            val entryAlias = entryAlias(userId, deviceId, alias)

            if (!standardKeyStore.containsAlias(entryAlias)) throw KeyStoreEntryNotFound(
                userId,
                deviceId,
                alias,
            )

            if (!standardKeyStore.isKeyEntry(entryAlias)) throw InvalidKeyStoreEntry(
                userId,
                deviceId,
                alias,
            )

            return standardKeyStore.getEntry(entryAlias, null) as? KeyStore.PrivateKeyEntry
                ?: throw KeyStoreException("could not load private key.")
        }


        private fun entryAlias(
            userId: String,
            deviceId: String,
            alias: String,
        ): String {
            return "$userId::$deviceId::$alias"
        }

        private fun getSignature(signatureAlgorithm: String): Signature {
            val algorithmImpl = when (signatureAlgorithm) {
                "SHA512" -> "SHA512with${Constants.KEY_ALGORITHM}"
                else -> "${signatureAlgorithm}with${Constants.KEY_ALGORITHM}"
            }

            return Signature.getInstance(algorithmImpl)
        }

        private fun secureRandom(): SecureRandom {
            val secureRandom = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                SecureRandom.getInstanceStrong()
            } else {
                SecureRandom()
            }
            return secureRandom
        }
    }
}