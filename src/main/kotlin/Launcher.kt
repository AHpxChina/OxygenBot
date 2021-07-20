import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.PlainText

suspend fun main(args : Array<String>){
    val bot = BotFactory.newBot(args[0].toLong(), args[1])
    bot.login()

    GlobalEventChannel.subscribeAlways<GroupMessageEvent> {
        if (it.message.any{ x -> x is PlainText }){
            for (text in it.message.filterIsInstance<PlainText>()) {
                if (text.content.lowercase().contains("hello")){
                    it.subject.sendMessage("Hello, World!")
                }
            }
        }
    }

    while (true){
        if (readLine() == "exit"){
            bot.close()
            return
        }
    }
}