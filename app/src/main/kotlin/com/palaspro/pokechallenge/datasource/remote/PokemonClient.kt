package com.palaspro.pokechallenge.datasource.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import me.sargunvohra.lib.pokekotlin.client.ErrorResponse
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList
import me.sargunvohra.lib.pokekotlin.model.Pokemon

const val CLIENT_POKEMON_TAG = "pokemonClient"

interface PokemonClient {

    fun getPokemonList(page: Int): Either<Error, NamedApiResourceList>

    fun getPokemonDetail(id: Int): Either<Error, Pokemon>
}

class PokemonClientImpl(private val pokeApiClient: PokeApiClient) : PokemonClient {

    companion object {
        const val LIMIT = 12
    }

    override fun getPokemonList(page: Int): Either<Error, NamedApiResourceList> =
            try {
                pokeApiClient.getPokemonList(page * LIMIT, LIMIT).right()
            } catch (e: ErrorResponse) {
                Error().left()
            }


    override fun getPokemonDetail(id: Int): Either<Error, Pokemon> =
            try {
                pokeApiClient.getPokemon(id).right()
            } catch (e: ErrorResponse) {
                Error().left()
            }

}