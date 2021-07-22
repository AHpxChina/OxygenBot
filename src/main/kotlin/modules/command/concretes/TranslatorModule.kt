package modules.command.concretes

import data.command.TranslationText
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import modules.command.CommandBase
import net.mamoe.mirai.contact.Contact
import utils.getParameters
import utils.toJsonString

class TranslatorModule : CommandBase {
    override suspend fun execute(raw: String, subject: Contact) {

    }

    override val executors: Sequence<String>
        get() = sequenceOf("/translate", "/trans", "/翻译翻译", "/翻译", "师爷，翻译翻译")
}

fun main(args : Array<String>){
//    val endpoint = "https://api.cognitive.microsofttranslator.com"
//    val key = "<api key>"
//    val route = "/translate?api-version=3.0&from=zh&to=ru"
//    val requestBody = listOf(TranslationText("混蛋")).toJsonString()
//    val client = HttpClient()
//
//    val response = GlobalScope.async {
//        client.post<String>("$endpoint$route"){
//            headers{
//                append("Ocp-Apim-Subscription-Key", key)
//                append("Ocp-Apim-Subscription-Region", "global")
//                append("Content-type", "application/json")
//            }
//            body = requestBody
//        }
//    }
//
//    println(response.await())

    val g = TranslatorModule()
    println(g.getParameters("/translate awkjdhawkjdhawdkjlhawdlkjhawd awd awdawd"))
}