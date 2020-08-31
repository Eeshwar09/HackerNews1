package com.example.retrorxjava.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.retrorxjava.base.BaseViewModel
import com.example.retrorxjava.home.helper.UseCaseResult
import com.example.retrorxjava.home.model.News
import com.example.retrorxjava.home.network.HackerNewsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val hackerNewsApi: HackerNewsApi) : BaseViewModel() {
    private var compositeDisposable = CompositeDisposable()
    val newsList = MutableLiveData<List<News>>()

    fun loadNewsData() {

        compositeDisposable.add(
            hackerNewsApi.getNews().subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe(
                { result ->
                    val news = result.items
                    newsList.value = news
                    UseCaseResult.Success(newsList)
                },
                {
                    UseCaseResult.Error(it)
                }
            )
        )

    }

}