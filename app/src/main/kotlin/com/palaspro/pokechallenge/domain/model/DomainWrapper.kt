package com.palaspro.pokechallenge.domain.model

import com.palaspro.pokechallenge.datasource.model.PokemonEntity
import me.sargunvohra.lib.pokekotlin.model.Pokemon

fun Pokemon.toPokemonEntity() =
        PokemonEntity(
                id = id,
                name = name,
                category = species.category,
                urlImage = sprites.frontDefault)