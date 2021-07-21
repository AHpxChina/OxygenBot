import data.command.CommandExecuteType
import modules.command.CommandBase
import modules.command.concretes.*
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.SimpleServiceMessage
import net.mamoe.mirai.utils.MiraiExperimentalApi

@MiraiExperimentalApi
suspend fun main(args : Array<String>){
    val bot = BotFactory.newBot(args[0].toLong(), args[1])
    bot.login()

    val modules = listOf<CommandBase>(BasicCommandModule(),
        GithubThumbnailModule(),
        PoemModule(),
        CovidReporterModule(),
        BullshitGenerator())

    GlobalEventChannel.subscribeAlways<MessageEvent> {
        for (text in it.message) {
            for (module in modules) {
                val predicate = when (module.executorType) {
                    CommandExecuteType.Contains -> {s:String -> text.contentToString().lowercase().contains(s)}
                    CommandExecuteType.Equals -> {s: String -> text.contentToString() == s}
                }

                if (module.executors.any(predicate)){
                    module.execute(text.contentToString(), it.subject)
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