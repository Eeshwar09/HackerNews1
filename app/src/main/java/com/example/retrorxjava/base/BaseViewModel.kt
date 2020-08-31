package com.example.retrorxjava.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()

    }

}