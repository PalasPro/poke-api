package com.palaspro.pokechallenge.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.palaspro.pokechallenge.datasource.room.dao.PokemonDao
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.datasource.model.PokemonEntity

@Database(entities = [PokemonEntity::class], version = Constants.DataBase.BD_VERSION)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao() : PokemonDao

}