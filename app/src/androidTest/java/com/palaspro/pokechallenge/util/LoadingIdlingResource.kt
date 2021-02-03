package com.palaspro.pokechallenge.util

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import com.palaspro.pokechallenge.R
import com.palaspro.pokechallenge.presenter.features.main.view.activity.MainActivity

class LoadingIdlingResource(private val mainActivity: MainActivity?) : IdlingResource {

    private var resourceCallback: ResourceCallback? = null
    private var isIdle = false

    override fun getName(): String = LoadingIdlingResource::class.java.name

    override fun isIdleNow(): Boolean {
        if (isIdle) return true

        val swipeRefreshLayout = mainActivity?.findViewById<SwipeRefreshLayout>(R.id.mainSwipeRefresh)

        isIdle = !(swipeRefreshLayout?.isRefreshing ?: false)
        if (isIdle) {
            resourceCallback?.onTransitionToIdle()
        }
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback?) {
        this.resourceCallback = callback
    }
}