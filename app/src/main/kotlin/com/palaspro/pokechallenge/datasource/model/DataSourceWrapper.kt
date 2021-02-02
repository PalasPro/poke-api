package com.palaspro.pokechallenge.datasource.model

import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

fun NamedApiResource.toPokemonEntity() =
        PokemonEntity(id, name, category)