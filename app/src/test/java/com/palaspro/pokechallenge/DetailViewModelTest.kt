package com.palaspro.pokechallenge

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.palaspro.pokechallenge.domain.repository.PokemonRepository
import com.palaspro.pokechallenge.presenter.features.detail.navigator.DetailNavigator
import com.palaspro.pokechallenge.presenter.features.detail.viewmodel.DetailViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject


class DetailViewModelTest : KoinTest {

    private val viewModel: DetailViewModel by inject()
    private var idPokemon = 0
    private lateinit var mockRepository: PokemonRepository
    private lateinit var mockNavigator: DetailNavigator

    @Before
    fun before() {
        mockRepository = mock()
        mockNavigator = mock()
        startKoin {
            modules(
                listOf(
                    module(override = true) {
                        factory { DetailViewModel(idPokemon, mockNavigator, mockRepository) }
                    }
                )
            )
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `check that on create activity call load more data`() {
        // when
        viewModel.onCreateActivity()
        // then
        verifyBlocking(mockRepository) {
            getPokemonDetail(idPokemon)
        }
    }

}