package com.palaspro.pokechallenge.datasource.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.palaspro.pokechallenge.datasource.remote.service.PokemonUserService
import retrofit2.Retrofit

const val CLIENT_USER_POKEMON_TAG = "pokemonUserClient"
const val RETROFIT_USER_POKEMON_TAG = "retrofitPokemonUserClient"
const val URL_BASE_USER_POKEMON = "https://webhook.site/"

interface PokemonUserClient {

    suspend fun changeFavoriteStatus(id: Int, favorite: Boolean): Either<Error, Boolean>

}

class PokemonUserClientImpl(private val retrofit: Retrofit) : PokemonUserClient {

    override suspend fun changeFavoriteStatus(id: Int, favorite: Boolean): Either<Error, Boolean> {
        return try {
            val result = retrofit.create(PokemonUserService::class.java).changeFavoriteStatus(id, favorite)
            result.isSuccessful.right()
        }catch (e : Exception) {
            e.printStackTrace()
            Error("Server error, try later.").left()
        }
    }

}