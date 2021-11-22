//[Project Jano - Android library](../../../index.md)/[io.jano.mobile.libs.android](../index.md)/[JanoSDK](index.md)/[getCertificates](get-certificates.md)

# getCertificates

[androidJvm]\
fun [getCertificates](get-certificates.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultDeviceId, alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)&gt;&gt;

Retrieve the stored certificates for the given tuple: userId, deviceId and alias.

#### Return

a list of X509 certificates

## Parameters

androidJvm

| | |
|---|---|
| userId | identifier of the user who owns the certificates |
| deviceId | identifier of this device. Default value is JanoSDK.DefaultDeviceId |
| alias | identifier of the certificates. Default value is JanoSDK.DefaultAlias |
