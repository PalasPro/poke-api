package com.palaspro.pokechallenge.presenter.features.detail.navigator

import android.widget.Toast
import com.palaspro.pokechallenge.presenter.base.BaseNavigator
import com.palaspro.pokechallenge.presenter.features.detail.view.activity.DetailActivity

interface DetailNavigator : BaseNavigator {

    /**
     * Show errors, in this case, with toast
     */
    fun showError(message: String)
}

class DetailNavigatorImpl(val activity: DetailActivity) : DetailNavigator {

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}