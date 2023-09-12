package com.example.utilis.api

import okhttp3.ResponseBody
import org.json.JSONObject

/**
 *
 * {
 *     "error": {
 *         "message": (String),
 *         "status_code": (int),
 *         "code": (int | null),
 *         "payload": (object | null) // the payload you sent to the server
 *     },
 *     "success": false
 * }
 *
 * **/

fun ResponseBody.handleError(): String {
    val json = JSONObject(this.string())
    val error = json.optJSONObject("error") ?: return "NO_ERROR"
    val errorMessage = error.optString("message")
    val statusCode = error.optInt("status_code")
    val code = error.optInt("code")
    val payload = error.optJSONObject("payload")

    if (errorMessage.isNullOrEmpty().not())
        return errorMessage

    if (payload != null)
        return payload.toString()

    if (statusCode != 0)
        return statusCode.toString()

    if (code != 0)
        return code.toString()

    return "NO_ERROR"
}


data class CustomErrorThrow(val key: String, val value: String) : Throwable()