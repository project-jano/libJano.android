package io.jano.mobile.libs.android.exceptions

internal class InvalidKeyStoreEntryException(userId: String, deviceId: String, alias: String) :
    Exception("Alias '$alias' for user '$userId' in device '$deviceId' is not of expected type.")
