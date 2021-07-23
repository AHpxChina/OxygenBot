package utils

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun getAzurePath(): Path = Path.of(".", "secrets", "azure.json").toAbsolutePath().normalize()

private data class AzureKeys(val key : String)

fun getAzureTranslationKey() : String{
    val json = Files.readString(getAzurePath()).toJsonObject()

    return json.get("key").asString
}

fun writeAzureTranslationKey(key : String){
    println(AzureKeys(key).toJsonString())

    Files.createDirectory(getAzurePath().parent)
    Files.writeString(getAzurePath(), AzureKeys(key).toJsonString())
}

fun getCurrentDir(): Path = Paths.get(".").toAbsolutePath().normalize()