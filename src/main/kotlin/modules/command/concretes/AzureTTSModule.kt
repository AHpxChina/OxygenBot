package modules.command.concretes

import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import net.mamoe.mirai.message.data.Voice
import utils.getCurrentDir
import java.nio.file.Path

//class AzureTTSModule {
//    init {
//        //https://docs.microsoft.com/en-us/azure/cognitive-services/speech-service/language-support
//    }
//}

fun main(args: Array<String>){
    val config = SpeechConfig.fromSubscription(args[0], "eastus")

    config.speechSynthesisVoiceName = "zh-CN-XiaoxiaoNeural"

//    val audioConfig = AudioConfig.fromWavFileOutput(Path.of("${getCurrentDir()}", "test.wav").normalize().toString())
    val audioConfig = AudioConfig.fromDefaultSpeakerOutput()

    val synthesizer = SpeechSynthesizer(config, audioConfig)

    synthesizer.SpeakText("这是一段中文测试文本")
}