package com.palaspro.pokechallenge.presenter.features.main.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.domain.repository.PokemonRepository
import com.palaspro.pokechallenge.presenter.base.BaseViewModel
import com.palaspro.pokechallenge.presenter.features.main.navigator.MainNavigator
import com.palaspro.pokechallenge.presenter.model.toListItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
        private var navigator: MainNavigator,
        private var repository: PokemonRepository
) : BaseViewModel() {

    /**
     * Current page to request
     */
    var page = 0

    /**
     * Listening the data source changes
     */
    val pokemonList = repository.getPokemonListFlow().map {
        it.toListItems(page > 0)
    }

    /**
     * This function is called in the creation of an activity
     */
    override fun onCreateActivity() {
        loadMorePokemon()
    }

    /**
     * Force to reset the data loaded, starting in the first page
     */
    fun resetLoadingPokemon() {
        page = 0
        loadMorePokemon(true)
    }

    /**
     * Request a page of pokemon
     */
    fun loadMorePokemon(forceRefresh : Boolean = false) {
        if (page == 0) {
            loadingMutable.value = true
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.loadPokemonPage(page, forceRefresh).fold(::handleError, ::handleSuccess)
        }
    }

    /**
     * Navigation to detail of a Pokemon
     */
    fun navigateToDetail(id: Int) {
        navigator.navigateToDetail(id)
    }

    private fun handleSuccess(hasNext: Boolean) {
        if(hasNext) {
            page++
        } else {
            page = -1
        }
        Log.d(Constants.TAG, "new page: $page")
        viewModelScope.launch(Dispatchers.Main) {
            loadingMutable.value = false
        }
    }

    private fun handleError(error: Error) {
        viewModelScope.launch(Dispatchers.Main) {
            navigator.showError("Error loading Pokemon: ${error.message}")
            loadingMutable.value = false
        }
    }

}