package com.example.retrorxjava.home

import androidx.test.espresso.IdlingResource.ResourceCallback
import androidx.test.espresso.IdlingResource
import io.reactivex.annotations.Nullable
import java.util.concurrent.atomic.AtomicBoolean


class idilingResource {
}
class SimpleIdlingResource : IdlingResource {

    @Nullable
    @Volatile
    private var mCallback: ResourceCallback? = null

    // Idleness is controlled with this boolean.
    private val mIsIdleNow = AtomicBoolean(true)

    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return mIsIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback) {
        mCallback = callback
    }


    fun setIdleState(isIdleNow: Boolean) {
        mIsIdleNow.set(isIdleNow)
        if (isIdleNow && mCallback != null) {
            mCallback!!.onTransitionToIdle()
        }
    }
}