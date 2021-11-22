//[Project Jano - Android library](../../../index.md)/[io.jano.mobile.libs.android](../index.md)/[JanoSDK](index.md)/[decrypt](decrypt.md)

# decrypt

[androidJvm]\
fun [decrypt](decrypt.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultDeviceId, alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias, cipheredText: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;

Decrypts a message, using the public key, for the given tuple: userId, deviceId and alias

#### Return

the plain text message

## Parameters

androidJvm

| | |
|---|---|
| userId | identifier of the user who owns the certificates |
| deviceId | identifier of this device. Default value is JanoSDK.DefaultDeviceId |
| alias | identifier of the certificates. Default value is JanoSDK.DefaultAlias |
| cipheredText | the encrypted message encoded in Base64 |
