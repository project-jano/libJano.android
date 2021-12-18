package io.jano.mobile.libs.android

import android.content.Context
import android.content.Intent
import android.util.Base64
import io.jano.mobile.libs.android.exceptions.CertificateAlreadyExistsException
import io.jano.mobile.libs.android.exceptions.InvalidPayloadException
import io.jano.mobile.libs.android.exceptions.InvalidSecurePushNotificationException
import io.jano.mobile.libs.android.models.*
import io.jano.mobile.libs.android.security.Constants
import io.jano.mobile.libs.android.security.SecurityManager
import java.io.ByteArrayInputStream
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*


/**
 * JanoSDK
 *
 * @author Ezequiel (Kimi) Aceto - ezequiel.aceto@gmail.com
 *
 * @version 1.0.0
 * @since 1.0.0
 */
object JanoSDK {

    private const val CertificateFactoryDefaultType = "X.509"

    const val DefaultAlias = "default"

    /**
     * This is the default security option for key-pair
     * * User authentication is required
     * * User presence is required
     * * unlocked device is required
     * * key-pair is invalidated when biometric enrollment changes
     */
    val DefaultSecurityOptions = SecurityOptions(
        userAuthenticationRequired = true,
        userPresenceRequired = true,
        unlockedDeviceRequired = true,
        invalidatedByBiometricEnrollment = true,
        strongBoxBacked = false
    )

    /**
     * This security option disables all the minimum security options. Use only on Emulators and Tests.
     */
    val ZeroSecurityOptions = SecurityOptions(
        userAuthenticationRequired = false,
        userPresenceRequired = false,
        unlockedDeviceRequired = false,
        invalidatedByBiometricEnrollment = false,
    )

    /**
     * Service certificate used when signing certificates.
     */
    var serviceCertificate: X509Certificate? = null

    /**
     * Check if there are stored certificates for the given tuple: userId, deviceId and alias.
     * @param userId identifier of the user who owns the certificates
     * @param deviceId identifier of this device. Default value is JanoSDK.DefaultDeviceId
     * @param alias identifier of the certificates. Default value is JanoSDK.DefaultAlias
     * @return true if the certificates exist
     */
    fun hasCertificate(
        userId: String,
        deviceId: String,
        alias: String = DefaultAlias,
    ): Boolean {
        return SecurityManager.hasCertificates(
            userId = userId,
            deviceId = deviceId,
            alias = alias,
        )
    }

    /**
     * Retrieve the stored certificates for the given tuple: userId, deviceId and alias.
     * @param userId identifier of the user who owns the certificates
     * @param deviceId identifier of this device. Default value is JanoSDK.DefaultDeviceId
     * @param alias identifier of the certificates. Default value is JanoSDK.DefaultAlias
     * @return a list of X509 certificates
     */
    fun getCertificates(
        userId: String,
        deviceId: String,
        alias: String = DefaultAlias,
    ): Result<List<X509Certificate>> {
        return try {
            Result.success(
                SecurityManager.getCertificates(
                    userId = userId,
                    deviceId = deviceId,
                    alias = alias,
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Generates a key pair, with a self-signed certificate for the given tuple: userId, deviceId and alias
     * @param context: Android Context. Required in order to get application's packageName
     * @param userId identifier of the user who owns the certificates
     * @param deviceId identifier of this device. Default value is JanoSDK.DefaultDeviceId
     * @param alias identifier of the certificates. Default value is JanoSDK.DefaultAlias
     * @param subject name of the person or entity to whom the certificate is being issued. It will mostly the user name
     * @param notBefore certificate not valid before this date. Default value is 'now'
     * @param notAfter certificate not valid after this date. Default value is one year from 'now'
     * @param options security options to protect the access to the private key. Default value is JanoSDK.DefaultSecurityOptions
     * @param override overrides key-pair and certificate if exists. Defualt value is 'false'
     * @return the recently generated self-signed X509 certificates
     */
    fun generateCertificate(
        context: Context,
        userId: String,
        deviceId: String,
        alias: String = DefaultAlias,
        subject: String,
        notBefore: Date = Date(),
        notAfter: Date = Calendar.getInstance().run {
            add(Calendar.YEAR, 999)
            time
        },
        options: SecurityOptions = DefaultSecurityOptions,
        override: Boolean = false,
    ): Result<X509Certificate> {
        return try {
            if (!override && SecurityManager.hasCertificates(
                    userId = userId,
                    deviceId = deviceId,
                    alias = alias,
                )
            ) {
                throw CertificateAlreadyExistsException(
                    userId = userId,
                    deviceId = deviceId,
                    alias = alias,
                )
            }

            SecurityManager.generateCertificate(
                context = context,
                userId = userId,
                deviceId = deviceId,
                alias = alias,
                subject = subject,
                notBefore = notBefore,
                notAfter = notAfter,
                options = options
            )

            return Result.success(
                SecurityManager.getCertificates(
                    userId = userId,
                    deviceId = deviceId,
                    alias = alias,
                ).first()
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Generates certificate signing request for the given tuple: userId, deviceId and alias
     * @param context: Android Context. Required in order to get device information
     * @param userId identifier of the user who owns the certificates
     * @param deviceId identifier of this device. Default value is JanoSDK.DefaultDeviceId
     * @param alias identifier of the certificates. Default value is JanoSDK.DefaultAlias
     * @param defaultCertificate mark this certificate as default in the certificates database. Default value is 'true'
     * @return the a CertificateSigningRequest that can be serialised and sent to Jano's Users microservice
     */
    fun createCertificateSigningRequest(
        context: Context,
        userId: String,
        deviceId: String,
        alias: String = DefaultAlias,
        defaultCertificate: Boolean = true,
    ): CertificateSigningRequest {

        return CertificateSigningRequest.from(
            keyId = alias,
            cipher = Constants.CIPHER_TRANSFORMATION,
            signatureAlgorithm = Constants.SIGNATURE_ALGORITHM,
            default = defaultCertificate,
            request = SecurityManager.createCertificateSigningRequest(
                userId = userId,
                deviceId = deviceId,
                alias = alias
            ),
            device = Device.fromLocalDevice(context)
        )
    }

    /**
     * Updates the certificate chain for the given tuple: userId, deviceId and alias
     * @param userId identifier of the user who owns the certificates
     * @param deviceId identifier of this device. Default value is JanoSDK.DefaultDeviceId
     * @param alias identifier of the certificates. Default value is JanoSDK.DefaultAlias
     * @param certificatesChain the certificate chain that Jano's Users microservice returned when signing the certificate
     */
    fun updateWithCertificateChain(
        userId: String,
        deviceId: String,
        alias: String = DefaultAlias,
        certificatesChain: String,
    ): Result<List<X509Certificate>> {
        return try {

            val certs = certificatesChain
                .split(Constants.CERT_END)
                .map {
                    "$it${Constants.CERT_END}"
                }
                .filter {
                    it.startsWith(Constants.CERT_BEGIN) ||
                            it.startsWith("\n${Constants.CERT_BEGIN}")
                }
                .map {
                    it.replace(Constants.CERT_BEGIN, "")
                        .replace(Constants.CERT_END, "")
                        .replace("\n", "")
                }

            val x509Certs =
                certs
                    .map {
                        CertificateFactory.getInstance(CertificateFactoryDefaultType)
                            .generateCertificate(
                                ByteArrayInputStream(
                                    Base64.decode(
                                        it,
                                        Base64.DEFAULT
                                    )
                                )
                            ) as X509Certificate
                    }

            SecurityManager.setSignedCertificate(
                userId = userId,
                deviceId = deviceId,
                alias = alias,
                x509Certs
            )

            Result.success(
                x509Certs
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Encrypts a message, either for local or remote decryption, for the given tuple: userId, deviceId and alias
     * @param userId identifier of the user who owns the certificates
     * @param deviceId identifier of this device. Default value is JanoSDK.DefaultDeviceId
     * @param alias identifier of the certificates. Default value is JanoSDK.DefaultAlias
     * @param message the message to encrypt
     * @param forRemoteDecryption true if the encrypted message is intended to be decrypted server side. false if the encrypted message is intended to be decrypted on this (local) device.
     * @return the encrypted message encoded in Base64
     */
    fun encrypt(
        context: Context,
        userId: String,
        deviceId: String,
        alias: String = DefaultAlias,
        message: String,
        forRemoteDecryption: Boolean = true
    ): Result<SecuredPayload> {
        return try {
            Result.success(
                SecurityManager.encrypt(
                    context = context,
                    userId = userId,
                    deviceId = deviceId,
                    alias = alias,
                    message = message,
                    useServerCertificate = forRemoteDecryption,
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Decrypts an verifies a ciphered message using the public key for the given tuple: userId, deviceId and alias.
     * @param userId identifier of the user who owns the certificates
     * @param deviceId identifier of this device. Default value is JanoSDK.DefaultDeviceId
     * @param alias identifier of the certificates. Default value is JanoSDK.DefaultAlias
     * @param securedPayload the secured (encrypted) payload encoded in Base64
     * @param signature the signature of the securedPayload in Base64
     * @param isRemotePayload true if the secured payload for generated server side. False if it was generated on this device.
     * @return the decrypted payload, which contains the plain text message, a fingerprint and a timestamp
     */
    fun decrypt(
        userId: String,
        deviceId: String,
        alias: String = DefaultAlias,
        securedPayload: String,
        signature: String,
        isRemotePayload: Boolean = true
    ): Result<Payload> {
        return try {
            return Result.success(
                SecurityManager.decrypt(
                    userId = userId,
                    deviceId = deviceId,
                    alias = alias,
                    securedPayload = securedPayload,
                    signature = signature,
                    useServerCertificate = isRemotePayload,
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Decrypts a secure using the public key for the given tuple: userId, deviceId and alias.
     * @param userId identifier of the user who owns the certificates
     * @param deviceId identifier of this device. Default value is JanoSDK.DefaultDeviceId
     * @param alias identifier of the certificates. Default value is JanoSDK.DefaultAlias
     * @param securedPayload the secured (encrypted) payload encoded in Base64
     * @param signature the signature of the securedPayload in Base64
     * @param isRemotePayload true if the secured payload for generated server side. False if it was generated on this device.
     * @return the decrypted payload, which contains the plain text message, a fingerprint and a timestamp
     */
    fun decryptNotification(
        userId: String,
        deviceId: String,
        alias: String = DefaultAlias,
        intent: Intent): Intent? {

        return try {
            SecurityManager.decryptPushNotification(userId, deviceId, alias, intent)
        } catch (e: Exception) {
            null
        }
    }
}