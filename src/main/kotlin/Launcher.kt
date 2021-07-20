import data.command.CommandExecuteType
import modules.command.CommandBase
import modules.command.concretes.BasicCommandModule
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.PlainText

suspend fun main(args : Array<String>){
    val bot = BotFactory.newBot(args[0].toLong(), args[1])
    bot.login()

    val modules = listOf<CommandBase>(BasicCommandModule())

    GlobalEventChannel.subscribeAlways<GroupMessageEvent> {
        if (it.message.any{ x -> x is PlainText }){
            for (text in it.message.filterIsInstance<PlainText>()) {
                for (module in modules) {
                    val predicate = when (module.executorType) {
                        CommandExecuteType.Contains -> {s:String -> text.content.lowercase().contains(s)}
                        CommandExecuteType.Equals -> {s: String -> text.content == s}
                    }

                    if (module.executors.any(predicate)){
                        module.execute(text.content, it.subject)
                    }
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