package com.palaspro.pokechallenge.domain.repository

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.palaspro.pokechallenge.datasource.model.toPokemonEntity
import com.palaspro.pokechallenge.datasource.remote.PokemonClient
import com.palaspro.pokechallenge.datasource.room.dao.PokemonDao
import com.palaspro.pokechallenge.domain.model.toPokemonEntity
import kotlinx.coroutines.flow.flow
import me.sargunvohra.lib.pokekotlin.model.Pokemon

const val REPOSITORY_POKEMON_TAG = "pokemonRepository"

class PokemonRepository(
        private val pokemonClient: PokemonClient,
        private val pokemonDao: PokemonDao) {

    fun getPokemonListFlow() = pokemonDao.pokemonList()

    fun getPokemonDetailFlow(id: Int) = pokemonDao.pokemonDetailFlow(id)

    suspend fun loadPokemonPage(page: Int): Either<Error, Int> {
        return pokemonClient.getPokemonList(page).flatMap { namedApiResourceList ->
            val result = namedApiResourceList.results.map { resource ->
                val entity = resource.toPokemonEntity()
                pokemonDao.getPokemon(entity.id)?.let { oldEntity ->
                    if (oldEntity.urlImage == null) {
                        pokemonClient.getPokemonDetail(entity.id).map { pokemon ->
                            entity.urlImage = pokemon.sprites.frontDefault
                        }
                    } else {
                        entity.urlImage = oldEntity.urlImage
                    }
                } ?: run {
                    pokemonClient.getPokemonDetail(entity.id).map { pokemon ->
                        entity.urlImage = pokemon.sprites.frontDefault
                    }
                }
                entity
            }

            if (page == 0) {
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

    suspend fun getPokemonDetail(id: Int): Either<Error, Pokemon> {
        return pokemonClient.getPokemonDetail(id).flatMap { pokemon ->
            pokemonDao.insert(pokemon.toPokemonEntity())
            pokemon.right()
        }
    }
}