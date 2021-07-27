package modules.command.concretes

import modules.command.CommandBase
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.Group
import org.jsoup.Jsoup
import utils.getHttpString

class Olympic2021Module : CommandBase {
    override suspend fun execute(raw: String, subject: Contact) {
        subject as Group


    }

    override val executors: Sequence<String>
        get() = sequenceOf("/olympic", "/东京奥运会", "/奥运会", "/tokyo")
}

suspend fun main(args : Array<String>){
    //url:
    //https://olympics.com/tokyo-2020/olympic-games/zh/results/all-sports/medal-standings.htm

    val url = "https://olympics.com/tokyo-2020/olympic-games/zh/results/all-sports/medal-standings.htm"
    val response = getHttpString(url)

    println(response)

    val result = Jsoup.parse(response)

    println(result.select("""tr[role="row"]""").size)
}