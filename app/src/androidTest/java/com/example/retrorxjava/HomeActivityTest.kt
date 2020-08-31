@file:Suppress("DEPRECATION")

package com.example.retrorxjava

import android.os.SystemClock
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.retrorxjava.home.ui.HomeActivity
import com.example.retrorxjava.home.ui.NewsListAdapter
import junit.framework.Assert.assertEquals
import kotlinx.android.synthetic.main.activity_book.*
import kotlinx.android.synthetic.main.toolbar_back_arrow.view.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.junit.Before
import kotlin.concurrent.thread


@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    private var mIdlingResource: IdlingResource? = null
    @get:Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java)
    private var mockWebServer = MockWebServer()


    @Before
    fun setup() {
        mockWebServer.start(8080)
        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)
        activityScenario.onActivity { activity ->
            mIdlingResource = activity.getIdlingResource()
            IdlingRegistry.getInstance().register(mIdlingResource)
        }
       Thread.sleep(3000)


    }

    @Test
    fun homeActivityToolbarTitle_visible() {
        onView(withId(R.id.toolbar)).check(matches(ViewMatchers.isDisplayed()))
        val toolbar = activityRule.activity.findViewById<RelativeLayout>(R.id.toolbar)
        val toolbarTitlename = toolbar.titleName.text.toString()
        onView(ViewMatchers.withText(toolbarTitlename)).check(matches(ViewMatchers.withText("HackerNews")))
    }

    @Test
    fun RecyclerList_visible() {
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.setBody(FileReader.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockedResponse)


        onView(withId(R.id.news_list))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))


    }

    @Test
    fun listOfItems_Matches() {
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.setBody(FileReader.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockedResponse)
        assertEquals(
            "jbarrozo",
            activityRule.activity.news_list[3].findViewById<TextView>(R.id.author).text
        )
        assertEquals(
            "2020-08-28",
            activityRule.activity.news_list[1].findViewById<TextView>(R.id.datepublished).text
        )
        assertEquals(
            "Svxlink: Advanced repeater controller and Echolink software for Linux",
            activityRule.activity.news_list[3].findViewById<TextView>(R.id.newsTitle).text
        )
        val adapter = activityRule.activity.news_list.adapter
        assertEquals(20, adapter?.itemCount)
    }


    @Test
    fun webActivity_visible() {
        onView(withId(R.id.news_list)).perform(actionOnItemAtPosition<NewsListAdapter.NewsViewHolder>(5, click()))
        Thread.sleep(8000)
        onView(withId(R.id.toolbar)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.titleName)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.web_activity)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.backButton)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))



    }
    @Test
    fun webViewLoadedOrNot(){
        onView(withId(R.id.news_list)).perform(actionOnItemAtPosition<NewsListAdapter.NewsViewHolder>(1, click()))
        Thread.sleep(5000)
        onView(withId(R.id.webview)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun webActivityToolbarTitleMatchesOrNot() {
        onView(withId(R.id.news_list))
            .perform(actionOnItemAtPosition<NewsListAdapter.NewsViewHolder>(5, click()))
        Thread.sleep(5000)

        val t = activityRule.activity.findViewById<TextView>(R.id.titleName)
        assertEquals(
            "HackerNews",
            t.text.toString()
        )


    }


    @Test
    fun nextAndPreviousButtonsAtPosition0() {
        onView(withId(R.id.news_list)).perform(actionOnItemAtPosition<NewsListAdapter.NewsViewHolder>(0, click()))
        Thread.sleep(1000)
        onView(withId(R.id.next)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.previous)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))

    }

    @Test
    fun nextAndPreviousButtonsAtLastPosition() {
        val adapter = activityRule.activity.news_list.adapter
        val size = adapter?.itemCount
        val position = size?.minus(1)
        onView(withId(R.id.news_list))
            .perform(actionOnItemAtPosition<NewsListAdapter.NewsViewHolder>(position!!, click()))
        onView(withId(R.id.next)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
        onView(withId(R.id.previous)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))


    }
    @Test
    fun nextAndPreviousButtonsAtRemainingPositions() {
        onView(withId(R.id.news_list)).perform(actionOnItemAtPosition<NewsListAdapter.NewsViewHolder>(9, click()))
        onView(withId(R.id.next)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.previous)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    }
    @Test
    fun nextButtonClick(){
        onView(withId(R.id.news_list)).perform(actionOnItemAtPosition<NewsListAdapter.NewsViewHolder>(9, click()))
        onView(withId(R.id.next)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.webview)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.next)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.previous)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))



    }
    @Test
    fun prviousButtonClick(){
        onView(withId(R.id.news_list)).perform(actionOnItemAtPosition<NewsListAdapter.NewsViewHolder>(9, click()))
        onView(withId(R.id.previous)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.webview)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.next)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.previous)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))



    }
    @Test
    fun backToHomeActivity(){
        onView(withId(R.id.news_list)).perform(actionOnItemAtPosition<NewsListAdapter.NewsViewHolder>(9, click()))
        onView(withId(R.id.backButton)).perform(click())
        onView(withId(R.id.news_list)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    }


    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource)
            mockWebServer.shutdown()
        }
    }

}