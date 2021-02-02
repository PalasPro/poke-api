package com.palaspro.pokechallenge.presenter.features.detail.viewmodel

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.palaspro.pokechallenge.base.BaseViewModel
import com.palaspro.pokechallenge.domain.repository.PokemonRepository
import com.palaspro.pokechallenge.presenter.features.detail.navigator.DetailNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
        private val id : Int,
        private val navigator: DetailNavigator,
        private var repository: PokemonRepository) : BaseViewModel() {

    val pokemonDetail = repository.getPokemonDetailFlow(id).asLiveData(viewModelScope.coroutineContext)

    override fun onCreateActivity() {
        loadPokemonDetail()
    }

    private fun loadPokemonDetail() {
        viewModelScope.launch(Dispatchers.IO) {
//            repository.getPokemonDetail(id).fold(::handel)
        }
    }

}