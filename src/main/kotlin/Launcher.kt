import data.command.CommandExecuteType
import javassist.tools.reflect.Reflection
import modules.command.CommandBase
import modules.command.concretes.*
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.MiraiExperimentalApi
import org.reflections.Reflections
import utils.getAzurePath
import utils.writeAzureTranslationKey
import java.io.File
import java.nio.file.Files

@MiraiExperimentalApi
suspend fun main(args : Array<String>){
    val bot = BotFactory.newBot(args[0].toLong(), args[1])
    bot.login()

    val ref = Reflections("modules.command.concretes")

    val modules = ref.getSubTypesOf(CommandBase::class.java).map { it.newInstance() }.toList()

    if (!Files.exists(getAzurePath())){
        writeAzureTranslationKey(args[2])
    }

    GlobalEventChannel.subscribeAlways<MessageEvent> {
        for (text in it.message) {
            for (module in modules) {
                val predicate = when (module.executorType) {
                    CommandExecuteType.Contains -> {s:String -> text.contentToString().lowercase().contains(s)}
                    CommandExecuteType.Equals -> {s: String -> text.content.split(" ").any{ s1 -> s1 == s}}
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