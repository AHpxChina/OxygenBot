package modules.command.concretes

import data.command.CommandExecuteType
import modules.command.CommandBase
import net.mamoe.mirai.contact.Contact

class BasicCommandModule : CommandBase {
    override suspend fun execute(raw: String, subject: Contact) {
        subject.sendMessage("Command executed by: $raw")
    }

    override val executorType: CommandExecuteType
        get() = CommandExecuteType.Equals

    override val executors: Sequence<String>
        get() = sequenceOf("help", "帮助")
}