package com.palaspro.pokechallenge.presenter.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val loadingMutable: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = loadingMutable

    abstract fun onCreateActivity()
}