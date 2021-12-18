package io.jano.mobile.libs.android.models

import io.jano.mobile.libs.android.exceptions.InvalidPayloadException
import org.json.JSONObject

data class SecurePushNotification private constructor(
    val title: String? = null,
    val body: String? = null,
    val payload: Map<String, String>? = null,
) {
    companion object {
        internal fun from(plainText: String): SecurePushNotification {
            try {
                val jsonObject = JSONObject(plainText)

                val title = jsonObject.optString("title")
                val body = jsonObject.optString("body")

                val payload = HashMap<String, String>()

                jsonObject.optString("payload").let {
                    val payloadObject = JSONObject(it)
                    for (key in payloadObject.keys()) {
                        payload[key] = payloadObject.getString(key)
                    }
                }

                return SecurePushNotification(
                    title = if (title.isNullOrEmpty()) null else title,
                    body = if (body.isNullOrEmpty()) null else body,
                    payload = if (payload.isEmpty()) null else payload,
                )
            } catch (e: Exception) {
                throw InvalidPayloadException()
            }
        }
    }
}