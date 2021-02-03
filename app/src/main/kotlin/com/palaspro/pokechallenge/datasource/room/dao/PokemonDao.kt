package com.palaspro.pokechallenge.datasource.room.dao

import androidx.room.*
import com.palaspro.pokechallenge.datasource.model.PokemonEntity
import kotlinx.coroutines.flow.Flow

const val DAO_POKEMON_TAG = "pokemonDao"

@Dao
abstract class PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(pokemons: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(pokemon: PokemonEntity)

    @Query("DELETE FROM pokemon")
    abstract fun removeAll()

    @Query("SELECT * FROM pokemon WHERE id=:id")
    abstract fun getPokemon(id : Int) : PokemonEntity?

    @Query("SELECT * FROM pokemon")
    abstract fun pokemonListFlow(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon WHERE id=:id")
    abstract fun pokemonDetailFlow(id : Int): Flow<PokemonEntity?>


    @Transaction
    open fun update(pokemon: PokemonEntity) {
        // if the pokemon exists, update with the internal fileds
        getPokemon(pokemon.id)?.let { oldEntity ->
            pokemon.isFavorite = oldEntity.isFavorite
        }
        // insert
        insert(pokemon)
    }
}