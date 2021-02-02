package com.palaspro.pokechallenge.datasource.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.sargunvohra.lib.pokekotlin.model.PokemonAbility

@Entity(tableName = "pokemon")
class PokemonEntity(
        @PrimaryKey val id: Int,
        val name: String,
        val category: String,
        var urlImage : String? = null,
        val order : Int = 0,
        val weight : Int = 0,
        val height : Int = 0,
        val baseExperience : Int = 0,
        val abilities: List<String> = listOf(),
        val moves: List<String> = listOf(),
        val types :List<String> = listOf())