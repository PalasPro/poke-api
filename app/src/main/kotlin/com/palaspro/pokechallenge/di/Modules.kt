package com.palaspro.pokechallenge.di

import androidx.room.Room
import com.palaspro.pokechallenge.datasource.remote.CLIENT_POKEMON_TAG
import com.palaspro.pokechallenge.datasource.remote.PokemonClient
import com.palaspro.pokechallenge.datasource.remote.PokemonClientImpl
import com.palaspro.pokechallenge.datasource.room.PokemonDatabase
import com.palaspro.pokechallenge.datasource.room.dao.DAO_POKEMON_TAG
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.domain.repository.PokemonRepository
import com.palaspro.pokechallenge.domain.repository.PokemonRepositoryImpl
import com.palaspro.pokechallenge.domain.repository.REPOSITORY_POKEMON_TAG
import com.palaspro.pokechallenge.presenter.features.detail.navigator.DetailNavigator
import com.palaspro.pokechallenge.presenter.features.detail.navigator.DetailNavigatorImpl
import com.palaspro.pokechallenge.presenter.features.detail.view.activity.DetailActivity
import com.palaspro.pokechallenge.presenter.features.detail.viewmodel.DetailViewModel
import com.palaspro.pokechallenge.presenter.features.main.navigator.MainNavigator
import com.palaspro.pokechallenge.presenter.features.main.navigator.MainNavigatorImpl
import com.palaspro.pokechallenge.presenter.features.main.view.activity.MainActivity
import com.palaspro.pokechallenge.presenter.features.main.viewmodel.MainViewModel
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

// presentation layer dependencies
val modulesPresentation = module(override = true) {
    scope<MainActivity> {
        scoped<MainNavigator> { MainNavigatorImpl(get()) }
        viewModel { MainViewModel(get(), get(named(REPOSITORY_POKEMON_TAG))) }
    }
    scope<DetailActivity> {
        scoped<DetailNavigator> { DetailNavigatorImpl(get())}
        viewModel { (idPokemon : Int) -> DetailViewModel(idPokemon ,get(), get(named(REPOSITORY_POKEMON_TAG))) }
    }
}

// domain layer dependencies
val modulesDomain = module(override = true) {

    factory<PokemonRepository>(named(REPOSITORY_POKEMON_TAG)) { PokemonRepositoryImpl(get(named(CLIENT_POKEMON_TAG)), get(named(DAO_POKEMON_TAG))) }
}

// data source dependencies
val modulesDataSource = module(override = true) {

    single<PokemonClient>(named(CLIENT_POKEMON_TAG)) { PokemonClientImpl(PokeApiClient()) }

    single(named(DAO_POKEMON_TAG)) {
        Room.databaseBuilder(androidContext(), PokemonDatabase::class.java, Constants.DataBase.BD_NAME).build().pokemonDao()
    }
}
