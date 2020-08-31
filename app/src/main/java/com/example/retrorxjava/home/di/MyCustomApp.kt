package com.example.retrorxjava.home.di

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.logger.Level
import org.koin.core.module.Module


open class MyCustomApp: Application() {

    override fun onCreate() {

         super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyCustomApp)
            modules(listOf(hackerModule, viewModuel))
        }

    }

    internal fun loadModules(module: Module, block: () -> Unit) {
        loadKoinModules(module)
        block()
        unloadKoinModules(module)
    }

    open fun getBaseUrl() {

        return
    }


    open fun onCreates(app: Application?) {
        super.onCreate()
         startKoin {
           androidLogger(Level.DEBUG)
           androidContext(this@MyCustomApp)
           modules(listOf(hackerModule, viewModuel))
       }
    }
}