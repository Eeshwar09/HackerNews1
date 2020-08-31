@file:Suppress("DEPRECATION")

package com.example.retrorxjava.web.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.retrorxjava.R
import android.webkit.WebView
import android.view.View
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.toolbar_back_arrow.*
import com.example.retrorxjava.base.BaseActivity
import com.example.retrorxjava.home.model.News
import com.example.retrorxjava.home.ui.HomeActivity
import com.example.retrorxjava.web.viewmodel.WebViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList


@Suppress(
    "CAST_NEVER_SUCCEEDS", "DEPRECATION", "NAME_SHADOWING",
    "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE"
)
class WebActivity : BaseActivity() {
    private var position: Int = 0
    private var newsLists: Int? = null
    private var size: Int? = 0
    private var title1: String? = ""
    private var newsList: ArrayList<News>? = ArrayList()
    private val webViewModel by viewModel<WebViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val intent = this.intent
        position = intent.getIntExtra("Position", 0)
        newsList = intent.getSerializableExtra("BookList") as ArrayList<News>?
        newsLists = newsList?.size
        size = newsLists?.minus(1)
        newsList?.get(position)?.url?.let { webViewModel.loadUrl(it, webview) }
        title1 = newsList?.get(position)?.title
        titleName.text = title1
        initlizeViews()

        buttonsEnable()

        next.setOnClickListener {
            position++
            buttonsEnable()
            newsList?.get(position)?.url?.let { webViewModel.loadUrl(it, webview) }
            title1 = newsList?.get(position)?.title
            setScreenTitle(title1.toString())

        }
        previous.setOnClickListener {
            position--
            buttonsEnable()
            newsList?.get(position)?.url?.let { webViewModel.loadUrl(it, webview) }
            title1 = newsList?.get(position)?.title
            setScreenTitle(title1.toString())

        }

        backButton.setOnClickListener {
            startActivity(intentFor(this))

        }


    }

    private fun initlizeViews() {

        getBackButton()



    }


    override fun onBackPressed() {
        val myWebView = findViewById<WebView>(R.id.webview)
        if (myWebView != null && myWebView.canGoBack()) {
            position--
            val title = newsList!![position].title
            titleName.text = title
            myWebView.goBack()
            buttonsEnable()
            return
        } else {
            super.onBackPressed()
        }
    }


    private fun buttonsEnable() {
        when (position) {
            0 -> {
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE

            }
            size -> {
                next.visibility = View.INVISIBLE
                previous.visibility = View.VISIBLE

            }
            else -> {
                next.visibility = View.VISIBLE
                previous.visibility = View.VISIBLE

            }
        }
    }

    @SuppressLint("NewApi", "SetJavaScriptEnabled")


    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, HomeActivity::class.java).apply {

            }
        }
    }

}
