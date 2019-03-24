package br.com.pelikan.xapp.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

class FileUtils{

    companion object {
        fun readJsonFromAssetsFile(context : Context, fileName: String): String {
            val br = BufferedReader(InputStreamReader(context.assets.open(fileName)))
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