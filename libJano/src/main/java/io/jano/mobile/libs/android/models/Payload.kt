package io.jano.mobile.libs.android.models

import io.jano.mobile.libs.android.exceptions.InvalidPayloadFormat
import org.json.JSONObject

data class Payload constructor(
    val timestamp: Long,
    val message: String,
    val fingerprint: String,
) {
    fun toJSONString(): String {
        val j = JSONObject()
        j.put("timestamp", timestamp)
        j.put("message", message)
        j.put("fingerprint", fingerprint)
        return j.toString()
    }

    companion object {
        internal fun from(plainText: String): Payload {
            try {
                val jsonObject = JSONObject(plainText)

                val timestamp = jsonObject.getLong("timestamp")
                val message = jsonObject.getString("message")
                val fingerprint = jsonObject.getString("fingerprint")

                return Payload(
                    timestamp = timestamp,
                    message = message,
                    fingerprint = fingerprint,
                )
            } catch (e: Exception) {
                throw InvalidPayloadFormat()
            }
        }
    }
}