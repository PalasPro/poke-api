package com.palaspro.pokechallenge.domain.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.domain.repository.room.dao.PokemonDao
import com.palaspro.pokechallenge.domain.repository.room.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = Constants.BD_VERSION)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao() : PokemonDao

}