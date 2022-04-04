//[Project Jano - Android library](../../../index.md)/[io.jano.mobile.libs.android](../index.md)/[JanoSDK](index.md)/[decryptNotification](decrypt-notification.md)

# decryptNotification

[androidJvm]\
fun [decryptNotification](decrypt-notification.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), deviceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = DefaultAlias, intent: [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)): [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)?

Decrypts a secure using the public key for the given tuple: userId, deviceId and alias.

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
