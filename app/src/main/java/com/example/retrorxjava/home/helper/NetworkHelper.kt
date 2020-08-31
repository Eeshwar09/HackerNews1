@file:Suppress("DEPRECATION")

package com.example.retrorxjava.home.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object NetworkHelper {
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}