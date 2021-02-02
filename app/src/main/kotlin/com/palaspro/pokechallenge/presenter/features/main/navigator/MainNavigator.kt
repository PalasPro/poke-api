package com.palaspro.pokechallenge.presenter.features.main.navigator

import android.content.Intent
import android.widget.Toast
import com.palaspro.pokechallenge.base.BaseNavigator
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.presenter.features.detail.view.activity.DetailActivity
import com.palaspro.pokechallenge.presenter.features.main.view.activity.MainActivity

class MainNavigator(private val activity: MainActivity) : BaseNavigator() {

    fun navigateToDetail(id : Int) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(Constants.Extras.EXTRA_ID, id)
        activity.startActivity(intent)
    }

    fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}