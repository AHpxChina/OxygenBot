package modules.command.concretes

import com.google.gson.JsonObject
import modules.command.CommandBase
import net.mamoe.mirai.contact.Contact
import utils.getHttpJsonArray
import utils.getHttpJsonObject
import utils.parseCommandParameter
import java.lang.StringBuilder
import java.text.DateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class CovidReporterModule : CommandBase {
    ///covid <arg1> <arg2> <arg...>
    override val executors: Sequence<String>
        get() = sequenceOf("/covid", "/corona virus", "/新冠")

    override suspend fun execute(raw: String, subject: Contact) {
        var rawString = raw;
        for (executor in executors) {
            rawString = rawString.substringAfter(executor)
        }

        rawString = rawString.trim()

        val message = when {
            rawString.contains("rank") -> rank(rawString)
            rawString.contains("detail") -> detail(rawString)
            rawString.contains("global") -> global()
            else -> "错误的命令!"
        }

        subject.sendMessage(message)
    }

    private suspend fun rank(rawString: String): String {
        val parameters = rawString.parseCommandParameter("rank")
        val parameter = if (parameters[0].isNotEmpty()) parameters[0].toInt() else 5

        val array = getHttpJsonArray("https://corona.lmao.ninja/v2/countries?yesterday=false&sort=cases")
        val rank = array.filter { array.indexOf(it) < parameter }

        val message = StringBuilder("总感染人数最多的${parameter}个国家")

        message.append("(国家/大洲/总人口/总感染人数):\r\n")
        for (element in rank) {
            if (element is JsonObject) {
                with(message) {
                    append("${element.get("country").asString}/")
                    append("${element.get("continent").asString}/")
                    append("${element.get("population").asString}/")
                    append("${element.get("cases").asString}\r\n")
                }
            }
        }

        return message.toString()
    }

    private suspend fun detail(rawString: String): String {
        val parameters = rawString.parseCommandParameter("detail")
        val parameter = parameters[0].ifEmpty { "China" }

        val json =
            getHttpJsonObject("https://corona.lmao.ninja/v2/countries/${parameter}?yesterday=false&strict=false&query")

        val message = StringBuilder("${json.get("country").asString}(${json.get("continent").asString})今天的新冠疫情:\r\n")

        with(message) {
            append("总计感染人数: ${json.get("cases").asString}\r\n")
            append("总人口: ${json.get("population").asString}\r\n")
            append("今日感染人数: ${json.get("todayCases").asString}\r\n")
            append("总计死亡人数: ${json.get("deaths").asString}\r\n")
            append("今日死亡人数: ${json.get("todayDeaths").asString}\r\n")
            append("总计痊愈人数: ${json.get("recovered").asString}\r\n")
            append("今日痊愈人数: ${json.get("todayRecovered").asString}\r\n")
            append("现有患者人数: ${json.get("active").asString}\r\n")
            append("现有重症患者人数: ${json.get("critical").asString}\r\n")
        }

        return message.toString()
    }

    private suspend fun global(): String {
        val json =
            getHttpJsonObject("https://corona.lmao.ninja/v2/all?yesterday=false")
        val message = StringBuilder("${LocalTime.now()}\r\n")

        with(message) {
            append("总计感染人数: ${json.get("cases").asString}\r\n")
            append("总人口: ${json.get("population").asString}\r\n")
            append("今日感染人数: ${json.get("todayCases").asString}\r\n")
            append("总计死亡人数: ${json.get("deaths").asString}\r\n")
            append("今日死亡人数: ${json.get("todayDeaths").asString}\r\n")
            append("总计痊愈人数: ${json.get("recovered").asString}\r\n")
            append("今日痊愈人数: ${json.get("todayRecovered").asString}\r\n")
            append("现有患者人数: ${json.get("active").asString}\r\n")
            append("现有重症患者人数: ${json.get("critical").asString}\r\n")
        }

        return message.toString()
    }
}