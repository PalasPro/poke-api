package com.palaspro.pokechallenge.presenter.features.detail.navigator

import android.widget.Toast
import com.palaspro.pokechallenge.presenter.base.BaseNavigator
import com.palaspro.pokechallenge.presenter.features.detail.view.activity.DetailActivity

class DetailNavigator(val activity: DetailActivity) : BaseNavigator() {

    /**
     * Show errors, in this case, with toast
     */
    fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}