@file:Suppress("DEPRECATION")

package com.example.retrorxjava.home.ui

import android.content.Intent
import android.os.Build
import android.view.View
import androidx.test.core.app.ActivityScenario
import com.example.retrorxjava.AndroidTest
import junit.framework.Assert
import kotlinx.android.synthetic.main.activity_welcome.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(
    RobolectricTestRunner::class
)
@Config(sdk = [Build.VERSION_CODES.P])
class SplashActivityTest: AndroidTest<SplashActivity>() {
    override fun createActivityInstance(intent: Intent?): SplashActivity {
        return Robolectric.buildActivity(SplashActivity::class.java, intent)
            .create()
            .resume()
            .get()

    }

    @Before
    fun setup() {
        stopKoin()
        startKoin {
            ActivityScenario.launch(SplashActivity::class.java)
            setupActivity()
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun checkScreenContents() {
        assertEquals(View.VISIBLE, activity.splashScreen.visibility)
        assertEquals(View.VISIBLE, activity.splashProgress.visibility)
        assertEquals(View.VISIBLE, activity.splashIcon.visibility)
        assertEquals(View.VISIBLE, activity.welcomeText.visibility)
    }

    @Test
    fun testNextActivityWasLaunchedWithIntent() {
        assertNotNull("MainActivity is not instantiated", activity)
        synchronized(this) {
            try {

                Thread.sleep(4000)

            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
        val intent = Shadows.shadowOf(activity)?.peekNextStartedActivity()
        if (intent != null) {
            Assert.assertEquals(
                HomeActivity::class.java.canonicalName,
                intent.component?.className
            )
        }


    }


}