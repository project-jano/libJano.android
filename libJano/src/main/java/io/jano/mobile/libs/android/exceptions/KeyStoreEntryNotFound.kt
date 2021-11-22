package io.jano.mobile.libs.android.exceptions

internal class KeyStoreEntryNotFound(userId: String, deviceId: String, alias: String) :
    Throwable("Alias '$alias' for user '$userId' in device '$deviceId' not found.")
