package com.example.retrorxjava

import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.InputStreamReader

class EspressoCountingIdlingResource {
    object CountingIdlingResourceSingleton {

        private const val RESOURCE = "GLOBAL"

        @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

        fun increment() {
            countingIdlingResource.increment()
            GlobalScope.launch {
                // our network call starts
                delay(3000)
            }
        }

        fun decrement() {
            if (!countingIdlingResource.isIdleNow) {
                countingIdlingResource.decrement()
            }
        }
    }
}
class MockResponseFileReader(path: String) {
    val content: String
    init {
        val reader = InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}
