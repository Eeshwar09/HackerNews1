package com.example.retrorxjava.home.model

import java.io.Serializable

data class NewsResponse(
    var version: String? = null,
    var title: String? = null,
    var description: String? = null,
    var home_page_url: String? = null,
    var items: List<News>?= null
): Serializable