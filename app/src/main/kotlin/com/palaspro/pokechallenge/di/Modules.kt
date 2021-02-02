package com.palaspro.pokechallenge.di

import androidx.room.Room
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.domain.repository.PokemonRepository
import com.palaspro.pokechallenge.domain.repository.REPOSITORY_POKEMON_TAG
import com.palaspro.pokechallenge.datasource.remote.CLIENT_POKEMON_TAG
import com.palaspro.pokechallenge.datasource.remote.PokemonClient
import com.palaspro.pokechallenge.datasource.room.PokemonDatabase
import com.palaspro.pokechallenge.datasource.room.dao.DAO_POKEMON_TAG
import com.palaspro.pokechallenge.presenter.feature.detail.navigator.DetailNavigator
import com.palaspro.pokechallenge.presenter.feature.detail.view.DetailActivity
import com.palaspro.pokechallenge.presenter.feature.detail.viewmodel.DetailViewModel
import com.palaspro.pokechallenge.presenter.feature.main.navigator.MainNavigator
import com.palaspro.pokechallenge.presenter.feature.main.view.activity.MainActivity
import com.palaspro.pokechallenge.presenter.feature.main.viewmodel.MainViewModel
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

// presentation layer dependencies
val modulesPresentation = module(override = true) {
    scope<MainActivity> {
        scoped { MainNavigator(get()) }
        viewModel { MainViewModel(get(), get(named(REPOSITORY_POKEMON_TAG))) }
    }
    scope<DetailActivity> {
        scoped { DetailNavigator(get())}
        viewModel { DetailViewModel(get()) }
    }
}

val modulesDomain = module(override = true) {

    factory(named(REPOSITORY_POKEMON_TAG)) { PokemonRepository(get(named(CLIENT_POKEMON_TAG)), get(named(DAO_POKEMON_TAG))) }

}

val modulesDataSource = module(override = true) {

    single(named(CLIENT_POKEMON_TAG)) { PokemonClient(PokeApiClient()) }

    single(named(DAO_POKEMON_TAG)) {
        Room.databaseBuilder(androidContext(), PokemonDatabase::class.java, Constants.DataBase.BD_NAME).build().pokemonDao()
    }

}