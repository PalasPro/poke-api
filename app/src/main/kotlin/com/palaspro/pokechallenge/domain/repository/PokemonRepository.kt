package com.palaspro.pokechallenge.domain.repository

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.palaspro.pokechallenge.datasource.model.PokemonEntity
import com.palaspro.pokechallenge.datasource.model.toPokemonEntity
import com.palaspro.pokechallenge.datasource.remote.PokemonClient
import com.palaspro.pokechallenge.datasource.room.dao.PokemonDao
import com.palaspro.pokechallenge.domain.model.toPokemonEntity
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import me.sargunvohra.lib.pokekotlin.model.Pokemon

const val REPOSITORY_POKEMON_TAG = "pokemonRepository"

class PokemonRepository(
        private val pokemonClient: PokemonClient,
        private val pokemonDao: PokemonDao) {

    fun getPokemonListFlow() = pokemonDao.pokemonList()

    fun getPokemonDetailFlow(id: Int) = pokemonDao.pokemonDetailFlow(id)

    suspend fun loadPokemonPage(page: Int, forceRefresh : Boolean = false): Either<Error, Int> {
        return pokemonClient.getPokemonList(page).flatMap { namedApiResourceList ->
            val result = arrayListOf<PokemonEntity>()
            namedApiResourceList.results.asFlow().map { resource ->
                var entity = resource.toPokemonEntity()
                pokemonDao.getPokemon(resource.id)?.let { oldEntity ->
                    oldEntity.urlImage?.let {
                        entity = oldEntity
                    } ?: run {
                        pokemonClient.getPokemonDetail(resource.id).map { pokemon ->
                            entity = pokemon.toPokemonEntity()
                        }
                    }
                } ?: run {
                    pokemonClient.getPokemonDetail(resource.id).map { pokemon ->
                        entity = pokemon.toPokemonEntity()
                    }
                }
                entity
            }.collect {
                result.add(it)
            }

            if (forceRefresh) {
                pokemonDao.removeAll()
            }
            pokemonDao.insert(result)

            if (page * PokemonClient.LIMIT > namedApiResourceList.count) {
                -1
            } else {
                page + 1
            }.right()
        }
    }

    suspend fun getPokemonDetail(id: Int): Either<Error, Boolean> {
        return pokemonClient.getPokemonDetail(id).flatMap { pokemon ->
            pokemonDao.insert(pokemon.toPokemonEntity())
            true.right()
        }
    }
}