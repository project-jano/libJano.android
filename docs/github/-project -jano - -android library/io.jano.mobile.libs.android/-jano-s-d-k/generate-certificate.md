//[Project Jano - Android library](../../../index.md)/[io.jano.mobile.libs.android](../index.md)/[JanoSDK](index.md)/[generateCertificate](generate-certificate.md)

# generateCertificate

[androidJvm]\
fun [generateCertificate](generate-certificate.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias, subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), notBefore: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), notAfter: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Calendar.getInstance().run {
            add(Calendar.YEAR, 999)
            time
        }, options: [SecurityOptions](../-security-options/index.md) = DefaultSecurityOptions, override: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html)&lt;[X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)&gt;

Generates a key pair, with a self-signed certificate for the given tuple: userId, deviceId and alias

#### Return

the recently generated self-signed X509 certificates

## Parameters

androidJvm

| | |
|---|---|
| context | : Android Context. Required in order to get application's packageName |
| userId | identifier of the user who owns the certificates |
| deviceId | identifier of this device. Default value is JanoSDK.DefaultDeviceId |
| alias | identifier of the certificates. Default value is JanoSDK.DefaultAlias |
| subject | name of the person or entity to whom the certificate is being issued. It will mostly the user name |
| notBefore | certificate not valid before this date. Default value is 'now' |
| notAfter | certificate not valid after this date. Default value is one year from 'now' |
| options | security options to protect the access to the private key. Default value is JanoSDK.DefaultSecurityOptions |
| override | overrides key-pair and certificate if exists. Defualt value is 'false' |
