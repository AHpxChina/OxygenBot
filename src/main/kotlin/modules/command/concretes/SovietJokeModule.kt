package modules.command.concretes

import com.google.gson.Gson
import com.google.gson.JsonArray
import data.command.CommandExecuteType
import modules.command.CommandBase
import net.mamoe.mirai.contact.Contact

class SovietJokeModule : CommandBase {
    override suspend fun execute(raw: String, subject: Contact) {
        val jokes = SovietJokeModule::class.java.getResource("/SovietJoke.json")?.readText()
        val gson = Gson()

        val array = gson.fromJson(jokes, JsonArray::class.java)

        subject.sendMessage(array.toList().random().asString)
    }

    override val executors: Sequence<String>
        get() = sequenceOf("/sovietjoke", "/SovietJoke", "来点苏联笑话", "苏联笑话")
}