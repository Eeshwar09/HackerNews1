@file:Suppress("DEPRECATION")

package com.example.retrorxjava.web.ui

import android.content.Intent
import android.os.Build
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.example.retrorxjava.R
import com.example.retrorxjava.home.di.createOkHttpClient
import com.example.retrorxjava.home.di.retrofit
import com.example.retrorxjava.home.network.HackerNewsApi
import com.example.retrorxjava.AndroidTest
import com.example.retrorxjava.home.ui.HomeActivity
import com.example.retrorxjava.MyCustomAppTest
import com.example.retrorxjava.home.utils.AppConfig
import com.example.retrorxjava.web.viewmodel.WebViewModel
import junit.framework.Assert
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.toolbar_back_arrow.*
import kotlinx.android.synthetic.main.toolbar_back_arrow.view.*
import org.junit.After
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import retrofit2.Retrofit

@Suppress("DEPRECATION")
@RunWith(
    RobolectricTestRunner::class
)
@Config(application = MyCustomAppTest::class, sdk = [Build.VERSION_CODES.P])
class WebActivityTest: AndroidTest<WebActivity>() {
    private val app: MyCustomAppTest = ApplicationProvider.getApplicationContext()



    override fun createActivityInstance(intent: Intent?): WebActivity {
        return Robolectric.buildActivity(WebActivity::class.java, intent)
            .create()
            .resume()
            .get()

    }

    private val module = module {
        single {
            createOkHttpClient()
        }

        single { retrofit(AppConfig.baseUrl) }


        single {
            get<Retrofit>().create(HackerNewsApi::class.java)
        }
        viewModel {
            WebViewModel()
        }

    }

    @Before
    fun setUp() {
        stopKoin()
        startKoin {
            app.loadModules(module) {
                ActivityScenario.launch(WebActivity::class.java)
                setupActivity()
            }

        }


    }

    @Test
    fun shouldWebviewLoadedUrl() {
        val webView = activity.findViewById<WebView>(R.id.webview)
        webView.loadUrl("http://example.com")
        Assertions.assertThat(Shadows.shadowOf(webView).lastLoadedUrl).isEqualTo("http://example.com")
    }


    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun backButtonPressed() {

        val backButton = activity.findViewById<ImageButton>(R.id.backButton)
        backButton.performClick()
        val intent = Shadows.shadowOf(activity).peekNextStartedActivity()
        Assert.assertEquals(
            HomeActivity::class.java.canonicalName,
            intent.component?.className
        )


    }


    @Test
    fun checkScreen() {
        Assert.assertEquals(View.VISIBLE, activity.web_activity.visibility)
        Assert.assertEquals(View.VISIBLE, activity.next.visibility)
        Assert.assertEquals(View.VISIBLE, activity.toolbar.visibility)
    }
    @Test
    fun nextButtonClick(){
        val nextButton = activity.findViewById<Button>(R.id.next)
        nextButton.performClick()
        Assert.assertEquals(View.VISIBLE, activity.web_activity.visibility)
        Assert.assertEquals(View.VISIBLE, activity.next.visibility)
        Assert.assertEquals(View.VISIBLE, activity.previous.visibility)
        val webView = activity.findViewById<WebView>(R.id.webview)
        webView.loadUrl("http://example.com")
    }
    @Test
    fun previousButtonClick(){
        val previousButton = activity.findViewById<Button>(R.id.previous)
        previousButton.performClick()
        Assert.assertEquals(View.VISIBLE, activity.web_activity.visibility)
        Assert.assertEquals(View.VISIBLE, activity.next.visibility)
        Assert.assertEquals(View.VISIBLE, activity.previous.visibility)
        Assert.assertEquals(View.VISIBLE, activity.toolbar.visibility)
        val webView = activity.findViewById<WebView>(R.id.webview)
        webView.loadUrl("http://example.com")
    }

}