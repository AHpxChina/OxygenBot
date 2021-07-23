import java.nio.file.Path
import java.nio.file.Paths

fun main(){
    println(Paths.get(".").toAbsolutePath().normalize())
}