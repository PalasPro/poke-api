package com.palaspro.pokechallenge.presenter.features.detail.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.palaspro.pokechallenge.presenter.base.BaseViewModel
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.domain.repository.PokemonRepository
import com.palaspro.pokechallenge.presenter.features.detail.navigator.DetailNavigator
import com.palaspro.pokechallenge.presenter.model.toVisualObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailViewModel(
        private val id: Int,
        private val navigator: DetailNavigator,
        private var repository: PokemonRepository) : BaseViewModel() {

    /**
     * Listening the data source changes
     */
    val pokemonDetail = repository.getPokemonDetailFlow(id).map { pokemonEntity ->
        pokemonEntity?.toVisualObject()
    }

    /**
     * This function is called in the creation of an activity
     */
    override fun onCreateActivity() {
        loadPokemonDetail()
    }

    /**
     * Request the detail of a pokemon
     */
    fun loadPokemonDetail() {
        loadingMutable.value = true
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPokemonDetail(id).fold(::handleError) { handleSuccessLoad() }
        }
    }

    /**
     * Request to change the favorite status
     */
    fun changeFavoriteStatus() {
        loadingMutable.value = true
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeFavoriteStatus(id).fold(::handleError) {
                loadingMutable.value = false
                Log.d(Constants.TAG, "Pokemon preference changed.")
            }
        }
    }

    private fun handleSuccessLoad() {
        viewModelScope.launch(Dispatchers.Main) {
            loadingMutable.value = false
            Log.d(Constants.TAG, "Pokemon loaded.")
        }
    }

    private fun handleError(error: Error) {
        viewModelScope.launch(Dispatchers.Main) {
            loadingMutable.value = false
            navigator.showError("Error: ${error.message} ")
        }
    }


}