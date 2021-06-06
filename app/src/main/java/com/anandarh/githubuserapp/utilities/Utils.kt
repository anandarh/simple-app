package com.anandarh.githubuserapp.utilities

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class Utils {
    fun getJsonFromAssets(context: Context, fileName: String): String? {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.defaultCharset())
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}