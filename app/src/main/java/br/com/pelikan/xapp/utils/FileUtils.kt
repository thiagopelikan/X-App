package br.com.pelikan.xapp.utils

import br.com.pelikan.xapp.XAppApplication
import java.io.BufferedReader
import java.io.InputStreamReader

class FileUtils{

    companion object {
        fun readJsonFromAssetsFile(fileName: String): String {
            val br = BufferedReader(InputStreamReader(XAppApplication.getApplicationContext().assets.open(fileName)))
            var json : String
            try {
                val sb = StringBuilder()
                var line: String? = br.readLine()

                while (line != null) {
                    sb.append(line)
                    sb.append(System.getProperty("line.separator"))
                    line = br.readLine()
                }
                json = sb.toString()
            } finally {
                br.close()
            }
            return json
        }
    }

}