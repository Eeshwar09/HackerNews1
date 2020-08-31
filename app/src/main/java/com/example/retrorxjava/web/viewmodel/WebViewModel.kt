package com.example.retrorxjava.web.viewmodel

import android.annotation.SuppressLint
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.retrorxjava.base.BaseViewModel

@Suppress("NAME_SHADOWING", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
class WebViewModel : BaseViewModel() {
    @SuppressLint("SetJavaScriptEnabled")


    fun loadUrl(text: String, webView: WebView) {
        var redirect = false
        var loadingFinished = true

        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.setSupportZoom(true)
        webView.isVerticalScrollBarEnabled = false
        val data = Uri.parse(text)
        val url = data.toString()
        if (url.contains("pdf")) {
            val uRl = "https://docs.google.com/gview?embedded=true&url=$url"
            webView.loadUrl(uRl)

        } else {
            webView.loadUrl(url)
        }


        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (!loadingFinished) {
                    redirect = true
                }

                loadingFinished = false
                if (data.toString().contains(".pdf")) {
                    val data = "http://docs.google.com/gview?embedded=true&url=$data"
                    webView.loadUrl(data)
                }
                webView.loadUrl(data.toString())
                return true
            }


        }
    }


}