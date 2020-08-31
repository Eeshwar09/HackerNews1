@file:Suppress("DEPRECATION")

package com.example.retrorxjava.home.di

import android.preference.PreferenceManager
import com.example.retrorxjava.home.network.HackerNewsApi
import com.example.retrorxjava.home.utils.AppConfig
import com.example.retrorxjava.home.viewmodel.HomeViewModel
import com.example.retrorxjava.web.viewmodel.WebViewModel
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val hackerModule = module {
    single {
        val defaultSharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(get())
        defaultSharedPreferences
    }



    single {
        createOkHttpClient()
    }
    single { retrofit(AppConfig.baseUrl) }






    single {
        get<Retrofit>().create(HackerNewsApi::class.java)
    }


}

val gson = GsonBuilder().serializeNulls().create()!!
fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor).build()
}

fun retrofit(baseUrl:String): Retrofit {
    return Retrofit.Builder()
        .callFactory(OkHttpClient.Builder().build())
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(createOkHttpClient())
        .build()

}





val viewModuel = module(override = true) {
  viewModel {
     HomeViewModel(hackerNewsApi = get())
  }
    viewModel {
        WebViewModel()
    }
}