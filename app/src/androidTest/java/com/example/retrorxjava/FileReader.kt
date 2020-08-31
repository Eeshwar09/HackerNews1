@file:Suppress("DEPRECATION")

package com.example.retrorxjava

import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

object FileReader {

    fun readStringFromFile( fileName: String): String {
        try {
            val inputStream = (InstrumentationRegistry.getInstrumentation().targetContext
                .applicationContext as HackerTestApp).assets.open(fileName)

            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }
    fun getJson(path : String) : String {
        // Load the JSON response
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
    class MockResponseFileReader(path: String) {
        val content: String
        init {
            val reader = InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(path))
            content = reader.readText()
            reader.close()
        }
    }
}