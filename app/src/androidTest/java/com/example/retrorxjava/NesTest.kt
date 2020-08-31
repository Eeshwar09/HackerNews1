package com.example.retrorxjava

import android.app.Application
import androidx.test.core.app.ActivityScenario
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnitRunner
import com.example.retrorxjava.home.model.News
import com.example.retrorxjava.home.network.HackerNewsApi
import com.example.retrorxjava.home.ui.HomeActivity
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NesTest{
   }
class ApiTestRunner : AndroidJUnitRunner() {

    override fun callApplicationOnCreate(app: Application?) {
        super.callApplicationOnCreate(app)
        loadKoinModules(module {
            single(override = true) { createTestWebService<News>(get()) }
        })
    }

    private inline fun <reified T> createTestWebService(okHttpClient: OkHttpClient): HackerNewsApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:$MOCK_WEB_SERVER_PORT/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        return retrofit.create(HackerNewsApi::class.java)
    }

    companion object {
        const val MOCK_WEB_SERVER_PORT = 4007
    }
}