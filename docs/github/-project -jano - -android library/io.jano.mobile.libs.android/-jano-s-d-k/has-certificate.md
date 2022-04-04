//[Project Jano - Android library](../../../index.md)/[io.jano.mobile.libs.android](../index.md)/[JanoSDK](index.md)/[hasCertificate](has-certificate.md)

# hasCertificate

[androidJvm]\
fun [hasCertificate](has-certificate.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Check if there are stored certificates for the given tuple: userId, deviceId and alias.

#### Return

true if the certificates exist

## Parameters

androidJvm

| | |
|---|---|
| userId | identifier of the user who owns the certificates |
| deviceId | identifier of this device. Default value is JanoSDK.DefaultDeviceId |
| alias | identifier of the certificates. Default value is JanoSDK.DefaultAlias |
