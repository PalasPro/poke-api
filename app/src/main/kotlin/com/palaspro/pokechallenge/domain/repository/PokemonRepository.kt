package com.palaspro.pokechallenge.domain.repository

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.palaspro.pokechallenge.datasource.model.PokemonEntity
import com.palaspro.pokechallenge.datasource.model.toPokemonEntity
import com.palaspro.pokechallenge.datasource.remote.PokemonClient
import com.palaspro.pokechallenge.datasource.remote.PokemonUserClient
import com.palaspro.pokechallenge.datasource.room.dao.PokemonDao
import com.palaspro.pokechallenge.domain.model.toPokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

const val REPOSITORY_POKEMON_TAG = "pokemonRepository"

interface PokemonRepository {
    /**
     * Flow data of the table [PokemonEntity]. To listen any change in the database data about the list of pokemon
     * @return [Flow]
     */
    fun getPokemonListFlow() : Flow<List<PokemonEntity>>

    /**
     * Flow data of a pokemon
     * @param [id] of a pokemon
     * @return [Flow]
     */
    fun getPokemonDetailFlow(id: Int) : Flow<PokemonEntity?>

    /**
     * The method request a page list of Pokemons
     * @param [page] to request
     * @param [forceRefresh] boolean to reset the data source
     * @return [Either] If has more pages, return true, i.o.c, false. When an error happen, it will
     * be in the left side
     */
    suspend fun loadPokemonPage(page: Int, forceRefresh : Boolean = false): Either<Error, Boolean>

    /**
     * Request the detail of a Pokemon
     * @param [id] of a Pokemon
     * @return [Either] If it is success, return true, i.o.c, false. When an error happen, it will
     * be in the left side
     */
    suspend fun getPokemonDetail(id: Int): Either<Error, Boolean>

    /**
     * Request a change in the favorite status of a pokemon
     * @param [id] of a Pokemon
     * @return [Either] If it is success, return true, i.o.c, false. When an error happen, it will
     */
    suspend fun changeFavoriteStatus(id: Int): Either<Error, Boolean>
}

class PokemonRepositoryImpl(
        private val pokemonClient: PokemonClient,
        private val pokemonUserClient : PokemonUserClient,
        private val pokemonDao: PokemonDao) : PokemonRepository {

    override fun getPokemonListFlow() = pokemonDao.pokemonListFlow()

    override fun getPokemonDetailFlow(id: Int) = pokemonDao.pokemonDetailFlow(id)

    override suspend fun loadPokemonPage(page: Int, forceRefresh : Boolean): Either<Error, Boolean> {
        return pokemonClient.getPokemonList(page).flatMap { namedApiResourceList ->
            val result = arrayListOf<PokemonEntity>()
            namedApiResourceList.results.asFlow().map { resource ->
                var entity = resource.toPokemonEntity()
                pokemonDao.getPokemon(resource.id)?.let { oldEntity ->
                    oldEntity.urlImage?.let {
                        entity = oldEntity
                    } ?: run {
                        pokemonClient.getPokemonDetail(resource.id).map { pokemon ->
                            entity = pokemon.toPokemonEntity(oldEntity.isFavorite)
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


            (namedApiResourceList.next != null).right()
        }
    }

    override suspend fun getPokemonDetail(id: Int): Either<Error, Boolean> {
        return pokemonClient.getPokemonDetail(id).flatMap { pokemon ->
            pokemonDao.update(pokemon.toPokemonEntity())
            true.right()
        }
    }

    override suspend fun changeFavoriteStatus(id: Int): Either<Error, Boolean> =
        pokemonDao.getPokemon(id)?.let { pokemonEntity ->
            pokemonUserClient.changeFavoriteStatus(id, !pokemonEntity.isFavorite).flatMap {
                pokemonEntity.isFavorite = !pokemonEntity.isFavorite
                pokemonDao.insert(pokemonEntity)
                true.right()
            }
        }?: run {
            Error("The pokemon is not able.").left()
        }
}