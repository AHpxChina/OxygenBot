package utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

fun String.fetchJsonArrayAsString(key : String) : List<String>{
    try {
        var json = Gson().fromJson(this, JsonObject::class.java).get(key)
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