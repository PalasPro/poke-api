package com.palaspro.pokechallenge.domain.model

import com.palaspro.pokechallenge.datasource.model.PokemonEntity
import me.sargunvohra.lib.pokekotlin.model.Pokemon

fun Pokemon.toPokemonEntity() =
        PokemonEntity(
                id = id,
                name = name,
                category = species.category,
                urlImage = sprites.frontDefault,
                order = order,
                weight = weight,
                height = height,
                baseExperience = baseExperience,
                abilities = abilities.map { it.ability.name },
                moves = moves.map { it.move.name },
                types = types.map { it.type.name })