package utils

fun String.parseCommandParameter(origin : String): List<String>{
    return this.substringAfter(origin).trim().split(" ");
}