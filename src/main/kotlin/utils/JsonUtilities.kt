package utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject

fun String.fetchJsonArrayAsString(key : String) : List<String>{
    try {
        val json = Gson().fromJson(this, JsonObject::class.java).get(key)
        val re = mutableListOf<String>()

        if (json is JsonArray){
            json.forEach { re.add(it.asString) }
        }

        return re
    }
    catch (e : Exception){
        e.printStackTrace()
    }

    return listOf()
}

fun Any.toJsonString(): String {
    val gson = Gson()
    return gson.toJson(this)
}

fun String.toJsonObject(): JsonObject {
    val gson = Gson()

    return gson.fromJson(this, JsonObject::class.java)
}

fun String.toJsonArray(): JsonArray {
    val gson = Gson()

    return gson.fromJson(this, JsonArray::class.java)
}
