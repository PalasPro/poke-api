package com.palaspro.pokechallenge.datasource.model

import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

/**
 * Translate a [NamedApiResource] to a [PokemonEntity]
 */
fun NamedApiResource.toPokemonEntity() =
        PokemonEntity(id, name, category)