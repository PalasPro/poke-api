package com.palaspro.pokechallenge.di

import androidx.room.Room
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.domain.repository.PokemonRepository
import com.palaspro.pokechallenge.domain.repository.room.PokemonDatabase
import com.palaspro.pokechallenge.feature.detail.navigator.DetailNavigator
import com.palaspro.pokechallenge.feature.detail.view.DetailActivity
import com.palaspro.pokechallenge.feature.detail.viewmodel.DetailViewModel
import com.palaspro.pokechallenge.feature.main.navigator.MainNavigator
import com.palaspro.pokechallenge.feature.main.view.MainActivity
import com.palaspro.pokechallenge.feature.main.viewmodel.MainViewModel
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

// presentation layer dependencies
val modulesPresentation = module(override = true) {
    scope<MainActivity> {
        scoped { MainNavigator(get()) }
        viewModel { MainViewModel(get()) }
    }
    scope<DetailActivity> {
        scoped { DetailNavigator(get())}
        viewModel { DetailViewModel(get()) }
    }
}

val modulesDomain = module(override = true) {

    factory { PokemonRepository(get(), get()) }

}

val modulesDataSource = module(override = true) {

    single { PokeApiClient() }

    single {
        Room.databaseBuilder(androidContext(), PokemonDatabase::class.java, Constants.BD_NAME).build()
    }
}