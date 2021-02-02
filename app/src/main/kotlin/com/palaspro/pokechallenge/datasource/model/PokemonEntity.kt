package com.palaspro.pokechallenge.datasource.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
class PokemonEntity(
        @PrimaryKey val id: Int,
        val name: String,
        val category: String,
        var urlImage : String? = null)