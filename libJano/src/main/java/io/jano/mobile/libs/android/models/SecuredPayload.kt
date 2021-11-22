package io.jano.mobile.libs.android.models

import org.json.JSONObject

data class SecuredPayload constructor(
    val keyId: String,
    val deviceId: String,
    val payload: String,
    val signature: String,
) {
    fun toJSONString(): String {
        return try {
            val json = JSONObject()
            json.put("keyId", keyId)
            json.put("deviceID", deviceId)
            json.put("payload", payload)
            json.put("signature", signature)
            json.toString()
        } catch (e: Exception) {
            "{}"
        }
    }
}

