/**
 * Project Jano - User microservice
 * This is the API of Project Jano
 *
 * OpenAPI spec version: 1.0.0
 * Contact: ezequiel.aceto+project-jano@gmail.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package io.jano.mobile.libs.android.models

import io.jano.mobile.libs.android.JanoSDK
import io.jano.mobile.libs.android.security.Constants

/**
 * A representation of Certificate Signing Request which includes additional information about the device and key-pair.
 * @param keyId Identifier of this certificate in the user's device
 * @param cipher Identifier if the transformation that should be used in the cipher
 * @param request Certificate Signing Request
 * @param device Information about the device
 * @param default true if this is the default credential for this user.
 */
data class CertificateSigningRequest private constructor(
    private val keyId: String,
    private val cipher: String,
    private val signatureAlgorithm: String,
    private val request: String,
    private val device: Device,
    private val default: Boolean = false
) {
    companion object {
        internal fun from(
            keyId: String = JanoSDK.DefaultAlias,
            cipher: String = Constants.CIPHER_TRANSFORMATION,
            signatureAlgorithm: String = Constants.SIGNATURE_ALGORITHM,
            request: String,
            device: Device,
            default: Boolean = false
        ): CertificateSigningRequest {
            if (keyId.isBlank()) throw IllegalArgumentException("keyId cannot be null or empty")
            if (cipher.isBlank()) throw IllegalArgumentException("cipher cannot be null or empty")
            if (signatureAlgorithm.isBlank()) throw IllegalArgumentException("signatureAlgorithm cannot be null or empty")
            if (request.isBlank()) throw IllegalArgumentException("request cannot be null or empty")

            return CertificateSigningRequest(
                keyId = keyId,
                cipher = cipher,
                signatureAlgorithm = signatureAlgorithm,
                request = request,
                device = device,
                default = default,
            )
        }
    }
}