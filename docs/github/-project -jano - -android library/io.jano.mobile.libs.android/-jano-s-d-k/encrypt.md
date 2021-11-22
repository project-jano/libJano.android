//[Project Jano - Android library](../../../index.md)/[io.jano.mobile.libs.android](../index.md)/[JanoSDK](index.md)/[encrypt](encrypt.md)

# encrypt

[androidJvm]\
fun [encrypt](encrypt.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultDeviceId, alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), forRemoteDecryption: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html)&lt;[SecuredPayload](../../io.jano.mobile.libs.android.models/-secured-payload/index.md)&gt;

Encrypts a message, either for local or remote decryption, for the given tuple: userId, deviceId and alias

#### Return

the encrypted message encoded in Base64

## Parameters

androidJvm

| | |
|---|---|
| userId | identifier of the user who owns the certificates |
| deviceId | identifier of this device. Default value is JanoSDK.DefaultDeviceId |
| alias | identifier of the certificates. Default value is JanoSDK.DefaultAlias |
| message | the message to encrypt |
| forRemoteDecryption | true if the encrypted message is intended to be decrypted server side. false if the encrypted message is intended to be decrypted on this (local) device. |
