package modules.command.concretes

import modules.command.CommandBase
import net.mamoe.mirai.contact.Contact
import utils.fetchJsonArrayAsString
import kotlin.random.Random


/*
    版权所有（c）2019 meng ke

    反996许可证版本1.0

    在符合下列条件的情况下，特此免费向任何得到本授权作品的副本（包括源代码、文件和/或相关内容，以
    下统称为“授权作品”）的个人和法人实体授权：被授权个人或法人实体有权以任何目的处置授权作品，包括
    但不限于使用、复制，修改，衍生利用、散布，发布和再许可：

    1. 个人或法人实体必须在许可作品的每个再散布或衍生副本上包含以上版权声明和本许可证，不得自行修
    改。
    2. 个人或法人实体必须严格遵守与个人实际所在地或个人出生地或归化地、或法人实体注册地或经营地（
    以较严格者为准）的司法管辖区所有适用的与劳动和就业相关法律、法规、规则和标准。如果该司法管辖区
    没有此类法律、法规、规章和标准或其法律、法规、规章和标准不可执行，则个人或法人实体必须遵守国际
    劳工标准的核心公约。
    3. 个人或法人不得以任何方式诱导、暗示或强迫其全职或兼职员工或其独立承包人以口头或书面形式同意
    直接或间接限制、削弱或放弃其所拥有的，受相关与劳动和就业有关的法律、法规、规则和标准保护的权利
    或补救措施，无论该等书面或口头协议是否被该司法管辖区的法律所承认，该等个人或法人实体也不得以任
    何方法限制其雇员或独立承包人向版权持有人或监督许可证合规情况的有关当局报告或投诉上述违反许可证
    的行为的权利。

    该授权作品是"按原样"提供，不做任何明示或暗示的保证，包括但不限于对适销性、特定用途适用性和非侵
    权性的保证。在任何情况下，无论是在合同诉讼、侵权诉讼或其他诉讼中，版权持有人均不承担因本软件或
    本软件的使用或其他交易而产生、引起或与之相关的任何索赔、损害或其他责任。
*/
class BullshitGenerator : CommandBase {
    //$prefix$
    //$suffix$
    //$subject$

    private val prefixes: List<String>
        get() = field.shuffled()

    //end with symbol
    private val suffixes: List<String>
        get() = field.shuffled()

    private val famous: List<String>
        get() = field.shuffled()

    //end with symbol
    private val shits: List<String>
        get() = field.shuffled()

    init {
        val data = BullshitGenerator::class.java.getResource("/BullshitData.json")?.readText()!!
        prefixes = data.fetchJsonArrayAsString("before")
        suffixes = data.fetchJsonArrayAsString("after")
        famous = data.fetchJsonArrayAsString("famous")
        shits = data.fetchJsonArrayAsString("shit")
    }

    private fun generateFamous(): String {
        var re = famous.random()

        re = re.replace("%prefix%", prefixes.random())
        re = re.replace("%suffix%", suffixes.random())

        return re;
    }

    private fun generateShit(subject: String) = shits.map { it.replace("%subject%", subject) }.random()

    fun generateBullshit(subject: String, length: Int = 1000): String {
        var re = ""

        while (re.length <= length) {
            val random = Random.nextInt(0, 100)

            re += "${generateShit(subject)}${generateFamous()}"
        }

        return re
    }

    override suspend fun execute(raw: String, subject: Contact) {
        var rawString = raw
        ;
        for (executor in executors) {
            rawString = rawString.substringAfter(executor)
        }

        rawString = rawString.trim()

        val parameters = rawString.split(" ")

        val title = parameters[0].ifEmpty { "" }

        var length = 1000;

        if (parameters.size > 1 && parameters[1].isNotEmpty()){
            length = parameters[1].toInt()
        }

        subject.sendMessage(generateBullshit(title, length))
    }

    override val executors: Sequence<String>
        get() = sequenceOf("/shit", "/generate shit", "/作文", "来点文豪力作")
}

suspend fun main(){
    val g = BullshitGenerator()

    val commander = "/shit"

    val cl = commander.substringAfter("/shit").trim().split(" ").toList()

    println(cl[0].isEmpty())
    println(cl[0].isNotBlank())
}