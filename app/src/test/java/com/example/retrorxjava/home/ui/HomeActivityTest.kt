@file:Suppress("DEPRECATION")

package com.example.retrorxjava.home.ui

import android.content.Intent
import android.os.Build
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.example.retrorxjava.AndroidTest
import com.example.retrorxjava.MyCustomAppTest
import com.example.retrorxjava.home.di.createOkHttpClient
import com.example.retrorxjava.home.di.retrofit
import com.example.retrorxjava.home.network.HackerNewsApi
import com.example.retrorxjava.home.utils.AppConfig
import com.example.retrorxjava.home.viewmodel.HomeViewModel
import junit.framework.Assert
import kotlinx.android.synthetic.main.activity_book.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.toolbar_back_arrow.*
import kotlinx.android.synthetic.main.toolbar_back_arrow.view.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit

@RunWith(
    RobolectricTestRunner::class
)
@Config(application = MyCustomAppTest::class, sdk = [Build.VERSION_CODES.P])

class HomeActivityTest: AndroidTest<HomeActivity>() {
    private val app: MyCustomAppTest = ApplicationProvider.getApplicationContext()

    override fun createActivityInstance(intent: Intent?): HomeActivity {

        return Robolectric.buildActivity(HomeActivity::class.java, intent)
            .create()
            .resume()
            .get()

    }


    val modules = module {
        single {
            createOkHttpClient()
        }

        single { retrofit(AppConfig.baseUrl) }


        single {
            get<Retrofit>().create(HackerNewsApi::class.java)
        }

        viewModel {
            HomeViewModel(hackerNewsApi = get())
        }
    }

    @Before
    fun setup() {
        app.loadModules(modules) {
            ActivityScenario.launch(HomeActivity::class.java)
            setupActivity()
        }
    }

    @Test
    fun checkScreenContents() {
        Assert.assertEquals(View.VISIBLE, activity.news_list.visibility)
        Assert.assertEquals(View.VISIBLE, activity.toolbar.visibility)
    }

    @Test
    fun toolbar_title() {
        val toolbar = activity.toolbar
        val toolbarTitle = toolbar.titleName.text.toString()
        assertEquals("HackerNews", toolbarTitle)

    }

    @Test
    fun itemViewClickListener() {

        val adapter = activity.news_list.adapter

        if (adapter != null) {
            Assert.assertEquals(0, adapter.itemCount)
        }
        val viewHolder = activity.news_list.findViewHolderForAdapterPosition(99)
        if (viewHolder != null) {
            viewHolder.itemView.performClick()
            Assert.assertEquals(View.VISIBLE, activity.author.visibility)


        }


    }

    @After
    fun tear_down() {
        stopKoin()
    }

}