//[Project Jano](../../../index.md)/[io.jano.mobile.libs.android](../index.md)/[JanoSDK](index.md)

# JanoSDK

[androidJvm]\
object [JanoSDK](index.md)

JanoSDK

#### Author

Ezequiel (Kimi) Aceto - ezequiel.aceto@gmail.com

#### Since

1.0.0

## Functions

| Name | Summary |
|---|---|
| [createCertificateSigningRequest](create-certificate-signing-request.md) | [androidJvm]<br>fun [createCertificateSigningRequest](create-certificate-signing-request.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultDeviceId, alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias, defaultCertificate: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [CertificateSigningRequest](../../io.jano.mobile.libs.android.models/-certificate-signing-request/index.md)<br>Generates certificate signing request for the given tuple: userId, deviceId and alias |
| [decrypt](decrypt.md) | [androidJvm]<br>fun [decrypt](decrypt.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultDeviceId, alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias, cipheredText: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Decrypts a message, using the public key, for the given tuple: userId, deviceId and alias |
| [encrypt](encrypt.md) | [androidJvm]<br>fun [encrypt](encrypt.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultDeviceId, alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), forRemoteDecryption: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Encrypts a message, either for local or remote decryption, for the given tuple: userId, deviceId and alias |
| [generateCertificate](generate-certificate.md) | [androidJvm]<br>fun [generateCertificate](generate-certificate.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultDeviceId, alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias, subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), notBefore: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), notAfter: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Calendar.getInstance().run {             add(Calendar.YEAR, 999)             time         }, options: [SecurityOptions](../-security-options/index.md) = DefaultSecurityOptions, override: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html)&lt;[X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)&gt;<br>Generates a key pair, with a self-signed certificate for the given tuple: userId, deviceId and alias |
| [getCertificates](get-certificates.md) | [androidJvm]<br>fun [getCertificates](get-certificates.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultDeviceId, alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)&gt;&gt;<br>Retrieve the stored certificates for the given tuple: userId, deviceId and alias. |
| [hasCertificate](has-certificate.md) | [androidJvm]<br>fun [hasCertificate](has-certificate.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultDeviceId, alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Check if there are stored certificates for the given tuple: userId, deviceId and alias. |
| [updateWithCertificateChain](update-with-certificate-chain.md) | [androidJvm]<br>fun [updateWithCertificateChain](update-with-certificate-chain.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultDeviceId, alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias, certificatesChain: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)&gt;&gt;<br>Updates the certificate chain for the given tuple: userId, deviceId and alias |

## Properties

| Name | Summary |
|---|---|
| [DefaultAlias](-default-alias.md) | [androidJvm]<br>const val [DefaultAlias](-default-alias.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [DefaultDeviceId](-default-device-id.md) | [androidJvm]<br>const val [DefaultDeviceId](-default-device-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [DefaultSecurityOptions](-default-security-options.md) | [androidJvm]<br>val [DefaultSecurityOptions](-default-security-options.md): [SecurityOptions](../-security-options/index.md)<br>This is the default security option for key-pair |
| [ZeroSecurityOptions](-zero-security-options.md) | [androidJvm]<br>val [ZeroSecurityOptions](-zero-security-options.md): [SecurityOptions](../-security-options/index.md)<br>This security option disables all the minimum security options. Use only on Emulators and Tests. |
