package com.example.retrorxjava

import com.example.retrorxjava.home.model.News
import com.example.retrorxjava.home.model.NewsResponse
import com.example.retrorxjava.home.network.HackerNewsApi
import io.reactivex.Observable
import io.reactivex.Single

class JsonRepository(private val api: HackerNewsApi) {
    fun observePosts(): Observable<NewsResponse> = api.getNews()

}