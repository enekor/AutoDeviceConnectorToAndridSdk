import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.writeLines

object Util {

    var sdkUri = ""
    val savedDataPath = Path.of("""${System.getProperty("user.dir")}${File.separator}data${File.separator}sdkpath.pathfile""")
    val savedDataDirPAth = Path.of("""${System.getProperty("user.dir")}${File.separator}data""")

    fun checkUriFile(uri:String):Boolean = Files.exists(Path.of(uri))

    fun connectPort(port: String): String {
        val comando = """$sdkUri${File.separator}platform-tools${File.separator}adb.exe connect $port"""
        println(comando)
        val command = ProcessBuilder()
        command.command("powershell","/c",comando)

        val proceso = command.start()
        val ans = BufferedReader(InputStreamReader(proceso.inputStream))

        return ans.readLine()
    }

    fun checkExistinfUriFile(): Boolean {
        return if(Files.exists(savedDataPath)){
            sdkUri = Files.readAllLines(savedDataPath)[0]
            true
        }else{
            false
        }
    }

    fun saveSdkUriFile(uri:String){
        if(!Files.exists(savedDataDirPAth)){
            Files.createDirectories(savedDataDirPAth)
        }

        if(!Files.exists(savedDataPath)){
            Files.createFile(savedDataPath)
        }

        Files.writeString(savedDataPath,uri)
    }
}