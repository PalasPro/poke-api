package com.palaspro.pokechallenge.presenter.features.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.palaspro.pokechallenge.base.BaseViewModel
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.domain.repository.PokemonRepository
import com.palaspro.pokechallenge.presenter.features.main.navigator.MainNavigator
import com.palaspro.pokechallenge.presenter.model.toListItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
        private var navigator: MainNavigator,
        private var repository: PokemonRepository
) : BaseViewModel() {

    private var page = 0

    val pokemonList = repository.getPokemonListFlow().asLiveData(viewModelScope.coroutineContext).map {
        it.toListItems(page > 0)
    }

    override fun onCreateActivity() {
        loadMorePokemon()
    }

    fun resetLoadingPokemon() {
        page = 0
        loadMorePokemon(true)
    }

    fun loadMorePokemon(forceRefresh : Boolean = false) {
        if (page == 0) {
            _loading.value = true
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.loadPokemonPage(page, forceRefresh).fold(::handleError, ::handleSuccess)
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
            navigator.showError("Error loading Pokemon: ${error.message}")
            _loading.value = false
        }
    }

    fun navigateToDetail(id: Int) {
        navigator.navigateToDetail(id)
    }

}