package modules.command.concretes

import data.command.TranslationText
import io.ktor.client.*
import io.ktor.client.request.*
import modules.command.CommandBase
import net.mamoe.mirai.contact.Contact
import utils.getAzureTranslationKey
import utils.getParameters
import utils.toJsonArray
import utils.toJsonString

class TranslatorModule : CommandBase {
    private val key = getAzureTranslationKey()
    override suspend fun execute(raw: String, subject: Contact) {
        var parameters = this.getParameters(raw)
        val text = getRequestBody(parameters)

        parameters = parameters.joinToString(" ")
            .replace(fetchText(parameters), "").trim()
            .split(" ")

        val target = parameters.first().ifEmpty { "zh" }

        val result = sendRequest(getRequestUrl(target), text).toJsonArray()
            .first().asJsonObject.get("translations").asJsonArray
            .first().asJsonObject.get("text").asString

        subject.sendMessage(result)
    }

    override val executors: Sequence<String>
        get() = sequenceOf("/translate", "/trans", "/翻译翻译", "/翻译", "师爷，翻译翻译")

    private fun getRequestBody(list: List<String>) =
        listOf(TranslationText(fetchText(list))).toJsonString()

    private fun fetchText(list: List<String>): String {
        return if (list.any { it.startsWith("\"") }) {
            val start = list.indexOfFirst { it.startsWith("\"") }
            val end = list.indexOfLast { it.endsWith("\"") } + 1

            return list.subList(start, end).joinToString(" ")
        } else {
            list[0]
        }
    }

    private fun getRequestUrl(target: String = "en"): String {
        return "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&to=$target"
    }

    private suspend fun sendRequest(url: String, content: String): String {
        val client = HttpClient()
        return client.post(url) {
            headers {
                append("Ocp-Apim-Subscription-Key", key)
                append("Ocp-Apim-Subscription-Region", "global")
                append("Content-type", "application/json")
            }
            body = content
        }
    }
}