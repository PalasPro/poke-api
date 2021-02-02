package com.palaspro.pokechallenge.domain.repository

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.palaspro.pokechallenge.datasource.remote.PokemonClient
import com.palaspro.pokechallenge.datasource.room.dao.PokemonDao
import com.palaspro.pokechallenge.datasource.model.PokemonEntity
import com.palaspro.pokechallenge.datasource.model.toPokemonEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sargunvohra.lib.pokekotlin.model.Pokemon

const val REPOSITORY_POKEMON_TAG = "pokemonRepository"

class PokemonRepository(
        private val pokemonClient: PokemonClient,
        private val pokemonDao: PokemonDao) {

    private val scope = CoroutineScope(Dispatchers.IO)

    fun pokemonList() = pokemonDao.pokemonList()

    suspend fun getPokemonList(page : Int): Either<Error, Int> {
        return pokemonClient.getPokemonList(page).flatMap { namedApiResourceList ->
            namedApiResourceList.results.map { resource ->
                resource.toPokemonEntity()
            }.forEach { entity ->
                pokemonDao.getPokemon(entity.id)?.let {
                    if(it.urlImage == null) {
                        scope.launch(Dispatchers.IO) { getPokemonDetail(entity.id) }
                    }
                } ?: run {
                    pokemonDao.insert(entity)
                    scope.launch(Dispatchers.IO) { getPokemonDetail(entity.id) }
                }
            }

            if(page * PokemonClient.LIMIT > namedApiResourceList.count) {
                -1
            } else {
                page+1
            }.right()
        }
    }

    suspend fun getPokemonDetail(id: Int) : Either<Error, Pokemon> {
        return pokemonClient.getPokemonDetail(id).flatMap { pokemon ->
            val pokemonEntity = PokemonEntity(
                    pokemon.id,
                    pokemon.name,
                    pokemon.species.category,
                    pokemon.sprites.frontDefault)

            pokemonDao.insert(pokemonEntity)

            pokemon.right()
        }
    }
}