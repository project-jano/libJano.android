package io.jano.mobile.libs.android.exceptions

internal class CertificateAlreadyExistsException(userId: String, deviceId: String, alias: String) :
    Throwable("Certificate for user '$userId' in device '$deviceId' with alias '$alias' already exists.")
