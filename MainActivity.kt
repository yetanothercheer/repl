package yetanothercheer.repl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.jruby.Ruby
import java.io.*
import java.lang.Exception
import java.net.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Thread {
//
//            NetworkInterface.getNetworkInterfaces().toList().forEach {
//                it.inetAddresses.toList().forEach {
//                    Log.e("> ", it.hostAddress)
//                }
//            }
//
//        }.start()

        Thread {
            Ruby.getGlobalRuntime()

            var exit = false
            val s = ServerSocket(10000, 50, InetAddress.getByName("192.168.1.2"))
            while (!exit) {
                val c = s.accept()
                val i = BufferedReader(InputStreamReader(c.getInputStream()))
                val o = PrintWriter(OutputStreamWriter(c.getOutputStream()))

                var endThis = false
                o.println("Welcome!")
                o.flush()
                while (!endThis) {
                    val cmd = i.readLine()
                    Log.e("In: ", cmd)
                    when (cmd) {
                        "exit" -> endThis = true
                        else -> {
                            try {
                                runOnUiThread {
                                    kotlin.runCatching {
                                        val r = Ruby.getGlobalRuntime().evalScriptlet(cmd)
                                        Log.e("Eval", r.toString())
                                        o.println("Android: " + r.toString())
                                    }
                                }
                                o.flush()
                            } catch (e: Exception) {
                                o.println(e.message)
                                o.flush()
                            }
                        }
                    }
                }

                o.println("Bye!")
                o.flush()
                c.close()
            }
        }.start()

        // Ruby.getGlobalRuntime().evalScriptlet("Java::AndroidUtil::Log.e 'Ruby', 'from ruby~'")
    }
}
