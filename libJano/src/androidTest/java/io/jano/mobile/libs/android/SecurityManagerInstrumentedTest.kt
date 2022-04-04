package io.jano.mobile.libs.android

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.jano.mobile.libs.android.security.SecurityManager
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SecurityManagerInstrumentedTest {
    @Test
    fun generateCSR() {

        val alias = "test"
        val startDate = Date()
        val issuer = "jano-test-issuer"

        val userId = "userId"
        val deviceId = "deviceId"

        val oneYear = Calendar.getInstance().run {
            add(Calendar.YEAR, 1)
            time
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val publicKey = SecurityManager.generateCertificate(
            context = context,
            userId = userId,
            deviceId = deviceId,
            alias = alias,
            subject = userId,
            notBefore = startDate,
            notAfter = oneYear,
            options = SecurityOptions(
                userAuthenticationRequired = false,
                userPresenceRequired = false,
                unlockedDeviceRequired = false,
                invalidatedByBiometricEnrollment = false,
            )
        )

        assert(publicKey.encoded.isNotEmpty())
        Assert.assertEquals("RSA", publicKey.algorithm)
        Assert.assertEquals("X.509", publicKey.format)

        val certificates = SecurityManager.getCertificates(
            userId = userId,
            deviceId = deviceId,
            alias = alias,
        )

        Assert.assertEquals(1, certificates.size)

        val certificate = certificates.first()

        Assert.assertEquals(3, certificate.version)
        assert(certificate.subjectDN.toString().contains("CN=$userId"))
        assert(certificate.subjectDN.toString().contains("UID=$userId"))
        assert(certificate.subjectDN.toString().contains("O=$issuer"))
        assert(certificate.subjectDN.toString().contains("OU=${context.packageName}"))
        assert(certificate.subjectDN.toString().contains("C=${Locale.getDefault().country}"))

        val csr = SecurityManager.createCertificateSigningRequest(
            userId = userId,
            deviceId = deviceId,
            alias = alias,
        )

        assert(csr.startsWith("-----BEGIN CERTIFICATE REQUEST-----\n"))
        assert(csr.endsWith("\n-----END CERTIFICATE REQUEST-----"))
    }
}
