package modules.command.concretes

import com.google.gson.Gson
import com.google.gson.JsonObject
import data.command.CommandExecuteType
import modules.command.CommandBase
import net.mamoe.mirai.contact.Contact
import utils.getHttpString

class PoemModule : CommandBase {
    override suspend fun execute(raw: String, subject: Contact) {
        val url = "https://v1.jinrishici.com/all.json";
        val response = getHttpString(url)

        val gson = Gson()
        val obj = gson.fromJson(response, JsonObject::class.java)

        val message = "${obj.get("content")} - ${obj.get("author").asString}"

        subject.sendMessage(message)
    }

    override val executors: Sequence<String>
        get() = sequenceOf("/poem", "/gimme a poem", "/give me a poem", "吟诗一句")
}