package com.palaspro.pokechallenge

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.palaspro.pokechallenge.domain.repository.PokemonRepository
import com.palaspro.pokechallenge.presenter.features.main.navigator.MainNavigator
import com.palaspro.pokechallenge.presenter.features.main.viewmodel.MainViewModel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject


class MainViewModelTest : KoinTest {

    private val viewModel: MainViewModel by inject()
    private lateinit var mockRepository: PokemonRepository
    private lateinit var mockNavigator: MainNavigator

    @Before
    fun before() {
        mockRepository = mock()
        mockNavigator = mock()
        startKoin {
            modules(
                listOf(
                    module(override = true) {
                        factory { MainViewModel(mockNavigator, mockRepository) }
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
            loadAndCachePokemonPage(viewModel.page)
        }
        Assert.assertEquals(0 , viewModel.page)
    }

    @Test
    fun `check that reset pages put zero in page filed and request the first page`() {
        viewModel.page = 23
        // when
        viewModel.resetLoadingPokemon()
        // then
        verifyBlocking(mockRepository) {
            loadAndCachePokemonPage(viewModel.page, true)
        }
        Assert.assertEquals(0 , viewModel.page)
    }

    @Test
    fun `check the navigation to go to detail`() {
        // when
        viewModel.navigateToDetail(any())
        // then
        verify(mockNavigator).navigateToDetail(any())
    }

}