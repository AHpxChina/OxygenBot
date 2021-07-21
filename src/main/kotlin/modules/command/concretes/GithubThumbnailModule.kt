package modules.command.concretes

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import modules.command.CommandBase
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import org.jsoup.Jsoup
import utils.getHttpInputStream
import java.io.InputStream

class GithubThumbnailModule : CommandBase {
    override suspend fun execute(raw: String, subject: Contact) {
        try {
            var url = raw
            if (raw.contains("<?xml")){

                url = Jsoup.parse(raw)
                    .selectFirst("msg[url~=github]")
                    ?.attributes()
                    ?.first{it.key == "url"}!!.value
            }

            if (!url.startsWith("https://")){
                url = "https://$url"
            }

            val doc = withContext(Dispatchers.IO){
                Jsoup.connect(url).get()
            }
            val node = doc.select("""meta[property="og:image"]""")[0]
            val content = node.attributes().first { it.key == "content" }.value

            val image = getHttpInputStream(content)
            subject.sendImage(image)

            image.close()
        }
        catch(e : Exception){
            e.printStackTrace()
        }
    }

    override val executors: Sequence<String>
        get() = sequenceOf("github.com", "awd")
}