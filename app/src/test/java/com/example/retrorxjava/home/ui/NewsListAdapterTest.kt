package com.example.retrorxjava.home.ui

import android.os.Build
import android.view.View
import com.example.retrorxjava.MyCustomAppTest
import com.example.retrorxjava.home.model.News
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("TYPEALIAS_EXPANSION_DEPRECATION")
@RunWith(
    RobolectricTestRunner::class
)
@Config(application = MyCustomAppTest::class, sdk = [Build.VERSION_CODES.P])
class NewsListAdapterTest{
    private lateinit var adapter: NewsListAdapter
    private lateinit var mockView: View
    private val newsList: ArrayList<News> = ArrayList()


    @Before
    @Throws(Exception::class)
    fun setUp() {
        stopKoin()
        startKoin {

            adapter = NewsListAdapter(newsList)
            mockView = Mockito.mock(View::class.java)

        }


    }

    @After
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun getItemCountReturnsNoItems() {
        val userList = emptyList<Any>()
        val initialExpected = 0
        assertEquals(initialExpected, userList.size)

    }

    @Test
    fun itemCount() {
        val news = News()
        adapter.setList(listOf(news, news, news))
        assertEquals(3, adapter.itemCount)
    }


    @Test
    fun getItemAtPosition() {
        val firstNews = News()
        val secondNews = News()
        adapter.setList(listOf(firstNews, secondNews))
        assertEquals(adapter.getItemAtPosition(0), firstNews)
        assertEquals(adapter.getItemAtPosition(1), secondNews)

    }


}