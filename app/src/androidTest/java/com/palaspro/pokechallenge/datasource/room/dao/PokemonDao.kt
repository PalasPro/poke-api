package com.palaspro.pokechallenge.datasource.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.palaspro.pokechallenge.datasource.model.PokemonEntity
import kotlinx.coroutines.flow.Flow

const val DAO_POKEMON_TAG = "pokemonDao"

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon")
    fun pokemonList(): Flow<List<PokemonEntity>>
}