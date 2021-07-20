package modules.command

import data.command.CommandExecuteType
import net.mamoe.mirai.contact.Contact

interface CommandBase{
    suspend fun execute(raw: String, subject: Contact)

    val executorType : CommandExecuteType
        get() = CommandExecuteType.Contains

    val executors : Sequence<String>
}