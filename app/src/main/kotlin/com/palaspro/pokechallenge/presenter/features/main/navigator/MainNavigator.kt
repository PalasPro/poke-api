package com.palaspro.pokechallenge.presenter.features.main.navigator

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.presenter.base.BaseNavigator
import com.palaspro.pokechallenge.presenter.features.detail.view.activity.DetailActivity
import com.palaspro.pokechallenge.presenter.features.main.view.activity.MainActivity

interface MainNavigator {
    /**
     * Navigation to detail of a Pokemon
     */
    fun navigateToDetail(id : Int, options : Bundle? = null)

    /**
     * Show errors, in this case, with toast
     */
    fun showError(message: String)
}

class MainNavigatorImpl(private val activity: MainActivity) : MainNavigator, BaseNavigator() {

    /**
     * Navigation to detail of a Pokemon
     */
    override fun navigateToDetail(id : Int, options : Bundle?) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(Constants.Extras.EXTRA_ID, id)
        activity.startActivity(intent)
    }

    /**
     * Show errors, in this case, with toast
     */
    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}