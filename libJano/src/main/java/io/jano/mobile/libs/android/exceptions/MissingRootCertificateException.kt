package io.jano.mobile.libs.android.exceptions

internal class MissingRootCertificateException(userId: String, deviceId: String, alias: String) :
    Throwable("Root Certificate for user '$userId' in device '$deviceId' with alias '$alias' does not exists.")
