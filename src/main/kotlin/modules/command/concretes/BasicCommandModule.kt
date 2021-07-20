package modules.command.concretes

import data.command.CommandExecuteType
import modules.command.CommandBase
import net.mamoe.mirai.contact.Contact

class BasicCommandModule : CommandBase {
    override suspend fun execute(raw: String, subject: Contact) {
        subject.sendMessage("OxygenBot是基于Mirai-core和使用了Kotlin开发的机器人。\r\n" +
                "这是破晓的第一个Kotlin项目，包含了一些他认为十分有用的功能。\r\n" +
                "代码已经开源在: https://github.com/AHpxChina/OxygenBot\r\n" +
                "现有的功能包括：没有功能😅\r\n" +
                "若要详细的说明，请使用/menu来查看")
    }

    override val executorType: CommandExecuteType
        get() = CommandExecuteType.Equals

    override val executors: Sequence<String>
        get() = sequenceOf("/help", "/帮助", "/idk", "/about", "OxygenBot", "oxygenbot")
}