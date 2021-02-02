package com.palaspro.pokechallenge.presenter.feature.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.palaspro.pokechallenge.base.BaseViewModel
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.domain.repository.PokemonRepository
import com.palaspro.pokechallenge.presenter.feature.main.navigator.MainNavigator
import com.palaspro.pokechallenge.presenter.model.toListItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
        private var navigator: MainNavigator,
        private var repository: PokemonRepository
) : BaseViewModel() {

    private var page = 0

    val pokemonList = repository.pokemonList().asLiveData(viewModelScope.coroutineContext).map {
        it.toListItems(page != -1)
    }

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun onCreateActivity() {
        loadMorePokemon()
    }

    fun resetLoadingPokemon() {
        page = 0
        loadMorePokemon()
    }

    fun loadMorePokemon() {
        if (page == 0) {
            _loading.value = true
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPokemonList(page).fold(::handleError, ::handleSuccess)
        }
    }

    private fun handleSuccess(newPage: Int) {
        Log.d(Constants.TAG, "new page: $newPage")
        page = newPage
        viewModelScope.launch(Dispatchers.Main) {
            _loading.value = false
        }
    }

    private fun handleError(error: Error) {
        viewModelScope.launch(Dispatchers.Main) {
            navigator.showError("Error loading Pòkemon")
            _loading.value = false
        }
    }

    fun navigateToDetail(id: Int) {
        navigator.navigateToDetail(id)
    }

}