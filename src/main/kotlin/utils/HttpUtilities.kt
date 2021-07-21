package utils

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