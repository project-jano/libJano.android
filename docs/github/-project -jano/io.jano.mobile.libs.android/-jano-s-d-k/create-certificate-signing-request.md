//[Project Jano](../../../index.md)/[io.jano.mobile.libs.android](../index.md)/[JanoSDK](index.md)/[createCertificateSigningRequest](create-certificate-signing-request.md)

# createCertificateSigningRequest

[androidJvm]\
fun [createCertificateSigningRequest](create-certificate-signing-request.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultDeviceId, alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias, defaultCertificate: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [CertificateSigningRequest](../../io.jano.mobile.libs.android.models/-certificate-signing-request/index.md)

Generates certificate signing request for the given tuple: userId, deviceId and alias

#### Return

the a CertificateSigningRequest that can be serialised and sent to Jano's Users microservice

## Parameters

androidJvm

| | |
|---|---|
| context | : Android Context. Required in order to get device information |
| userId | identifier of the user who owns the certificates |
| deviceId | identifier of this device. Default value is JanoSDK.DefaultDeviceId |
| alias | identifier of the certificates. Default value is JanoSDK.DefaultAlias |
| defaultCertificate | mark this certificate as default in the certificates database. Default value is 'true' |
