package com.palaspro.pokechallenge.presenter.features.detail.viewmodel

import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.palaspro.pokechallenge.base.BaseViewModel
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.domain.repository.PokemonRepository
import com.palaspro.pokechallenge.presenter.features.detail.navigator.DetailNavigator
import com.palaspro.pokechallenge.presenter.model.toVisualObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
        private val id: Int,
        private val navigator: DetailNavigator,
        private var repository: PokemonRepository) : BaseViewModel() {

    val pokemonDetail = repository.getPokemonDetailFlow(id).asLiveData(viewModelScope.coroutineContext).map { pokemonEntity ->
        pokemonEntity?.toVisualObject()
    }

    override fun onCreateActivity() {
        loadPokemonDetail()
    }

    fun loadPokemonDetail() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPokemonDetail(id).fold(::handleError) { handleSuccess() }
        }
    }

    private fun handleSuccess() {
        viewModelScope.launch(Dispatchers.Main) {
            _loading.value = false
            Log.d(Constants.TAG, "Pokemon loaded.")
        }
    }

    private fun handleError(error: Error) {
        viewModelScope.launch(Dispatchers.Main) {
            _loading.value = false
            navigator.showError("Error loadind detail. ${error.message} ")
        }
    }

}