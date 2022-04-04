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
package io.jano.mobile.tests.libs.application.api

import io.jano.mobile.libs.android.models.CertificateSigningRequest
import io.jano.mobile.tests.libs.application.api.openapi.ApiClient
import io.jano.mobile.tests.libs.application.api.openapi.ClientError
import io.jano.mobile.tests.libs.application.api.openapi.ClientException
import io.jano.mobile.tests.libs.application.api.openapi.RequestConfig
import io.jano.mobile.tests.libs.application.api.openapi.RequestMethod
import io.jano.mobile.tests.libs.application.api.openapi.ResponseType
import io.jano.mobile.tests.libs.application.api.openapi.ServerError
import io.jano.mobile.tests.libs.application.api.openapi.ServerException
import io.jano.mobile.tests.libs.application.api.openapi.Success
import java.net.URLEncoder

class SecurityAPI(basePath: String = "http://localhost/v2") : ApiClient(basePath) {

    private data class CertificateSigningResponse(val chain: String)

    /**
     * Signs a certificate request and returns the certificate chain as string
     * @param userId identifier of the user in your application
     * @param deviceId identifier of this device, may be the Push Notifications identifier
     * @param certificateSigningRequest true if this is the default certificate for this user
     * @return void
     */
    @Suppress("UNCHECKED_CAST")
    fun signCertificate(
        userId: String,
        deviceId: String,
        certificateSigningRequest: CertificateSigningRequest,
    ): Result<String> {

        val localVariableConfig = RequestConfig(
            RequestMethod.POST,
            "/users/{userId}/devices/{deviceId}/csr"
                .replace("{userId}", URLEncoder.encode(userId, Charsets.UTF_8.name()))
                .replace("{deviceId}", URLEncoder.encode(deviceId, Charsets.UTF_8.name()))
        )
        val response = request<CertificateSigningResponse>(
            localVariableConfig, certificateSigningRequest
        )

        return when (response.responseType) {
            ResponseType.Success -> {
                val serviceResponse = (response as Success<CertificateSigningResponse>).data
                Result.success(serviceResponse.chain)
            }
            ResponseType.Informational -> Result.failure(
                ClientException(
                    "Unimplemented response"
                )
            )
            ResponseType.Redirection -> Result.failure(
                ClientException(
                    "Unimplemented response"
                )
            )
            ResponseType.ClientError -> Result.failure(
                ClientException(
                    (response as ClientError<*>).body as? String ?: "Client error"
                )
            )
            ResponseType.ServerError -> Result.failure(
                ServerException(
                    (response as ServerError<*>).message ?: "Server error"
                )
            )
        }
    }
}
