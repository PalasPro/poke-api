package com.palaspro.pokechallenge.presenter.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel<T : BaseNavigator> : ViewModel() {

    protected val loadingMutable: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = loadingMutable

    lateinit var navigator : T

    open fun onCreateActivity(navigator: T) {
        this.navigator = navigator
    }
}