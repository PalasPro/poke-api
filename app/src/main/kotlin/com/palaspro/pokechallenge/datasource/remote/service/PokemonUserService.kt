package com.palaspro.pokechallenge.datasource.remote.service

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface PokemonUserService {

    @POST("/a1dfe050-4748-437d-8040-fe60f4917923")
    suspend fun changeFavoriteStatus(@Query("id") id : Int, @Query("isFavorite") isFavorite : Boolean) : Response<Void>
}