package com.example.retrorxjava.home.ui

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrorxjava.R
import com.example.retrorxjava.base.BaseActivity
import com.example.retrorxjava.home.helper.NetworkHelper
import com.example.retrorxjava.home.model.News
import com.example.retrorxjava.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_book.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.test.espresso.IdlingResource
import androidx.annotation.VisibleForTesting
import com.example.retrorxjava.EspressoCountingIdlingResource
import com.example.retrorxjava.home.SimpleIdlingResource
import io.reactivex.annotations.NonNull

import io.reactivex.annotations.Nullable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Suppress("CAST_NEVER_SUCCEEDS", "TYPEALIAS_EXPANSION_DEPRECATION")
class HomeActivity : BaseActivity() {
    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var adapter: NewsListAdapter
    private var newsInfoList: ArrayList<News> = ArrayList()

    @Nullable
    private var mIdlingResource: SimpleIdlingResource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        news_list.layoutManager = LinearLayoutManager(this)
        initlizeViews()
        when {
            NetworkHelper.isNetworkConnected(this) -> homeViewModel.newsList.observe(
                this,
                Observer {
                    EspressoCountingIdlingResource.CountingIdlingResourceSingleton.increment()
                   GlobalScope.launch {
                        // our network call starts
                        delay(3000)
                    }
                    setAdapter(it)

                })
            else -> {
                Toast.makeText(this, "No Internet!", Toast.LENGTH_SHORT).show()

            }
        }
        homeViewModel.loadNewsData()



    }

    private fun setAdapter(newsList: List<News>) {
        if (!::adapter.isInitialized) {
            adapter = NewsListAdapter(newsList)
            news_list.adapter = adapter
            adapter.notifyDataSetChanged()


        } else {
            adapter.setList(newsList)
        }

        newsInfoList = ArrayList(newsList)
    }

    private fun initlizeViews() {
        setScreenTitle(R.string.app_name)
    }
    @VisibleForTesting
    @NonNull
    fun getIdlingResource(): IdlingResource {
        if (mIdlingResource == null) {
            mIdlingResource= SimpleIdlingResource()
        }
        return mIdlingResource as SimpleIdlingResource
    }


}
