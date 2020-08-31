package com.example.retrorxjava

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.example.retrorxjava.home.ui.HomeActivity
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.*
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton


class MsinTest {


    @get:Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java, true, false)

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {


    }

    @Test
    fun testSuccessfulResponse() {
        //  activityRule.launchActivity(null)
        Thread.sleep(5000)


        Espresso.onView(ViewMatchers.withId(R.id.news_list))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
//        val adapter = activityRule.activity.news_list.adapter
//        Assert.assertEquals(20, adapter?.itemCount)
    }

    @Test
    fun testFailedResponse() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().throttleBody(1024, 55, TimeUnit.SECONDS)
            }
        }

         activityRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.news_list))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

    }

    @After
    fun teardown() {

    }
}

class TestNetModule() {
    private val mockWebServer: MockWebServer

    init {
        mockWebServer = MockWebServer()
    }


    @Singleton
    internal fun providerMockWebServer(): MockWebServer {
        return MockWebServer()
    }

    @Singleton
    internal fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        val okHttp = OkHttpClient.Builder()

        val interceptor = HttpLoggingInterceptor()
        val noCacheInterceptor = //NoCacheInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        if (!okHttp.interceptors().contains(interceptor))
            okHttp.addInterceptor(interceptor)

        if (!okHttp.interceptors().contains(noCacheInterceptor))
            okHttp.addInterceptor(noCacheInterceptor)

        return okHttp
    }


    internal fun provideRetrofitBuilder(
        httpClient: OkHttpClient.Builder,
        mockWebServer: MockWebServer
    ): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/").toString())
            .client(httpClient.build()).build()
    }
}

