package com.palaspro.pokechallenge.di

import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import org.koin.dsl.module

val modulesPokeChallenge = module {

    single() { PokeApiClient() }
}