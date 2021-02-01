package com.palaspro.pokechallenge.feature.main.navigator

import android.content.Intent
import com.palaspro.pokechallenge.base.BaseNavigator
import com.palaspro.pokechallenge.feature.detail.view.DetailActivity
import com.palaspro.pokechallenge.feature.main.view.MainActivity

class MainNavigator(private val activity: MainActivity) : BaseNavigator() {

    fun navigateToDetail(id : String) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("", id)
        activity.startActivity(intent)
    }
}