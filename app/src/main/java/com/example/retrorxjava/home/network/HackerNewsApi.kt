package com.example.retrorxjava.home.network

import com.example.retrorxjava.home.model.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface HackerNewsApi {

    @GET("frontpage.jsonfeed")
    fun getNews() : Observable<NewsResponse>
}