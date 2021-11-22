//[Project Jano - Android library](../../../index.md)/[io.jano.mobile.libs.android](../index.md)/[SecurityOptions](index.md)/[SecurityOptions](-security-options.md)

# SecurityOptions

[androidJvm]\
fun [SecurityOptions](-security-options.md)(userAuthenticationRequired: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, userPresenceRequired: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, unlockedDeviceRequired: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, invalidatedByBiometricEnrollment: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, strongBoxBacked: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

## Parameters

androidJvm

| | |
|---|---|
| userAuthenticationRequired | Sets whether this key is authorized to be used only if the user has been authenticated. Default value is 'true'. Ref.: https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec.Builder#setUserAuthenticationRequired(boolean) |
| userPresenceRequired | Sets whether a test of user presence is required to be performed between the Signature method calls. Ref.: https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec.Builder#setUserPresenceRequired(boolean) |
| unlockedDeviceRequired | Sets whether the keystore requires the screen to be unlocked before allowing decryption using this key. Default value is 'true'. Ref.: https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec.Builder#setUnlockedDeviceRequired(boolean) |
| invalidatedByBiometricEnrollment | Sets whether this key should be invalidated on biometric enrollment. Default value is 'true'. Ref.: https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec.Builder#setInvalidatedByBiometricEnrollment(boolean) |
| strongBoxBacked | Sets whether this key should be protected by a StrongBox security chip. Default value is 'false'. Ref.: https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec.Builder#setIsStrongBoxBacked(boolean) |
