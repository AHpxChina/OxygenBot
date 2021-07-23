package modules.command.concretes

import data.command.CommandExecuteType
import modules.command.CommandBase
import net.mamoe.mirai.contact.Contact
import utils.getParameters

class RandomChoiceModule : CommandBase {
    override suspend fun execute(raw: String, subject: Contact) {
        subject.sendMessage(this.getParameters(raw).random())
    }

    override val executors: Sequence<String>
        get() = sequenceOf("/choice", "/random choice", "随便选个")
}