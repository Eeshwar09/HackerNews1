package com.example.retrorxjava

import android.app.Application
import com.example.retrorxjava.home.di.MyCustomApp
import okhttp3.OkHttpClient
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmModuleProtoBuf
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import androidx.core.content.ContextCompat.getSystemService
import com.example.retrorxjava.home.model.News
import com.example.retrorxjava.home.viewmodel.HomeViewModel
import com.example.retrorxjava.web.viewmodel.WebViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module


class HackerTestApp:Application() {
     var url = "http://localhost:8080/"


     override fun onCreate() {
          super.onCreate()
          startKoin {
               loadKoinModules(module {
                    single(override = true) { createTestWebService<News>(get()) }

               })

          }

     }


      val data =  module {
           single(override = true) { createTestWebService<News>(get()) }

      }



     val viewModuel = module(override = true) {
          viewModel {
               HomeViewModel(hackerNewsApi = get())
          }
          viewModel {
               WebViewModel()
          }
     }

     private inline fun <reified T> createTestWebService(okHttpClient: OkHttpClient): T {
          val retrofit = Retrofit.Builder()
               .baseUrl("http://localhost:$MOCK_WEB_SERVER_PORT/")
               .client(okHttpClient)
               .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
          return retrofit.create(T::class.java)
     }

     companion object {
          const val MOCK_WEB_SERVER_PORT = 4007
     }



}