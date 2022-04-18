package io.jano.mobile.libs.android.exceptions

internal class KeyStoreEntryNotFoundException(userId: String, deviceId: String, alias: String) :
    Exception("Alias '$alias' for user '$userId' in device '$deviceId' not found.")
