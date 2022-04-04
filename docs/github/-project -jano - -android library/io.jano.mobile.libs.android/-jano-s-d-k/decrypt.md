//[Project Jano - Android library](../../../index.md)/[io.jano.mobile.libs.android](../index.md)/[JanoSDK](index.md)/[decrypt](decrypt.md)

# decrypt

[androidJvm]\
fun [decrypt](decrypt.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias, securedPayload: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), signature: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), isRemotePayload: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html)&lt;[Payload](../../io.jano.mobile.libs.android.models/-payload/index.md)&gt;

Decrypts an verifies a ciphered message using the public key for the given tuple: userId, deviceId and alias.

#### Return

the decrypted payload, which contains the plain text message, a fingerprint and a timestamp

## Parameters

androidJvm

| | |
|---|---|
| userId | identifier of the user who owns the certificates |
| deviceId | identifier of this device. Default value is JanoSDK.DefaultDeviceId |
| alias | identifier of the certificates. Default value is JanoSDK.DefaultAlias |
| securedPayload | the secured (encrypted) payload encoded in Base64 |
| signature | the signature of the securedPayload in Base64 |
| isRemotePayload | true if the secured payload for generated server side. False if it was generated on this device. |
