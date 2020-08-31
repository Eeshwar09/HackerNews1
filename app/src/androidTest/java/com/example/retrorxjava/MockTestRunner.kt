@file:Suppress("DEPRECATION", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.retrorxjava

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class MockTestRunner:AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader,
                                className: String,
                                context: Context): Application {
        return super.newApplication(cl,
            HackerTestApp::class.java.name,
            context)
    }


}
