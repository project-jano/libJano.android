package io.jano.mobile.libs.android.exceptions

internal class InvalidKeyStoreEntry(userId: String, deviceId: String, alias: String) :
    Throwable("Alias '$alias' for user '$userId' in device '$deviceId' is not of expected type.")
