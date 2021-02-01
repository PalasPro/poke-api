package com.palaspro.pokechallenge.domain.repository

import com.palaspro.pokechallenge.domain.repository.remote.PokemonClient
import com.palaspro.pokechallenge.domain.repository.room.PokemonDatabase

class PokemonRepository(
        private val pokemonClient: PokemonClient,
        private val pokeDataBase: PokemonDatabase) {


    suspend fun getPokemonList() {

    }
}