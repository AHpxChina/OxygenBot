package utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.io.InputStream

suspend fun getHttpString(url : String): String {
    val client = HttpClient()
    return client.get<HttpResponse>(url).receive()
}

suspend fun getHttpInputStream(url : String): InputStream {
    val client = HttpClient()
    return client.get<HttpResponse>(url).receive()
}

suspend fun getHttpJsonObject(url : String): JsonObject {
    val response = getHttpString(url)
    val gson = Gson()

    return gson.fromJson(response, JsonObject::class.java)
}

suspend fun getHttpJsonArray(url : String) : JsonArray{
    val response = getHttpString(url)
    val gson = Gson()

    return gson.fromJson(response, JsonArray::class.java)
}