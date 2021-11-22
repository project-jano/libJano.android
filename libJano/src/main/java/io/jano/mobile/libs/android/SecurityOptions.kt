package io.jano.mobile.libs.android

/**
 * Security options for the key-pair and certificates
 * @param userAuthenticationRequired Sets whether this key is authorized to be used only if the user has been authenticated. Default value is 'true'. Ref.: https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec.Builder#setUserAuthenticationRequired(boolean)
 * @param userPresenceRequired Sets whether a test of user presence is required to be performed between the Signature method calls. Ref.: https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec.Builder#setUserPresenceRequired(boolean)
 * @param unlockedDeviceRequired Sets whether the keystore requires the screen to be unlocked before allowing decryption using this key. Default value is 'true'. Ref.: https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec.Builder#setUnlockedDeviceRequired(boolean)
 * @param invalidatedByBiometricEnrollment Sets whether this key should be invalidated on biometric enrollment. Default value is 'true'. Ref.: https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec.Builder#setInvalidatedByBiometricEnrollment(boolean)
 * @param strongBoxBacked Sets whether this key should be protected by a StrongBox security chip. Default value is 'false'. Ref.: https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec.Builder#setIsStrongBoxBacked(boolean)
 */
data class SecurityOptions(
    val userAuthenticationRequired: Boolean = true,
    val userPresenceRequired: Boolean = true,
    val unlockedDeviceRequired: Boolean = true,
    val invalidatedByBiometricEnrollment: Boolean = true,
    val strongBoxBacked: Boolean = false,
)