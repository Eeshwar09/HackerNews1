package com.example.retrorxjava.home.viewmodel

import android.icu.lang.UCharacter
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.retrorxjava.RxImmediateSchedulerRule
import com.example.retrorxjava.home.di.hackerModule
import com.example.retrorxjava.home.model.News
import com.example.retrorxjava.home.model.NewsResponse
import com.example.retrorxjava.home.network.HackerNewsApi
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.OngoingStubbing

@Suppress("CAST_NEVER_SUCCEEDS")
class HomeViewModelTest : KoinTest {


    private val homeViewModelTest: HomeViewModel by inject()
    @Mock
    lateinit var hackerNewsApi: HackerNewsApi

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    private lateinit var p: List<News>


    companion object {
        @ClassRule
        @JvmField
        @get:Rule
        val schedulers = RxImmediateSchedulerRule()
    }


    @Before
    @Test
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        stopKoin()
        startKoin {
            modules(
                module {
                    single {
                        HomeViewModel(
                            hackerNewsApi = hackerNewsApi
                        )
                    }
                }
            )
        }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }


    }

    @After
    @Test
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun checkModule() {
        koinApplication { hackerModule }
        Assert.assertNotNull(hackerModule)

    }


    @Test
    fun `check api calls OnSuccess`() {
        val mockNews =
            listOf(News("76", "hacker", "HtML", "httpps", "www.google", "26-08-2020", "Eeshwar"))
        val mockNewsResponse = NewsResponse("2.2", "Veera", "aboutNews", "http//sspt", mockNews)
        homeViewModelTest.newsList.postValue(mockNews)
        whenever(hackerNewsApi.getNews()).thenReturn(Observable.just(mockNewsResponse))
        assertEquals(1, homeViewModelTest.newsList.value!!.size)
        assertEquals("Eeshwar", homeViewModelTest.newsList.value!![0].author)
        assertEquals("26-08-2020", homeViewModelTest.newsList.value!![0].date_published)
        assertEquals("hacker", homeViewModelTest.newsList.value!![0].title)


    }

    @Test
    fun `check api calls OnError`() {
        whenever(hackerNewsApi.getNews()).thenReturn(Observable.error(Throwable("error")))

        homeViewModelTest.loadNewsData()


    }


}


private inline fun <reified T> mock(): Int? =
    Mockito.mock(UCharacter.GraphemeClusterBreak.T::class.java)

fun <T> whenever(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)