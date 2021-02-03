package com.palaspro.pokechallenge

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.palaspro.pokechallenge.datasource.remote.CLIENT_POKEMON_TAG
import com.palaspro.pokechallenge.datasource.remote.PokemonClient
import com.palaspro.pokechallenge.datasource.room.dao.DAO_POKEMON_TAG
import com.palaspro.pokechallenge.datasource.room.dao.PokemonDao
import com.palaspro.pokechallenge.di.modulesDataSource
import com.palaspro.pokechallenge.di.modulesDomain
import com.palaspro.pokechallenge.domain.repository.PokemonRepository
import com.palaspro.pokechallenge.domain.repository.REPOSITORY_POKEMON_TAG
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class RepositoryTest : KoinTest {

    private val repository: PokemonRepository by inject(named(REPOSITORY_POKEMON_TAG))
    private lateinit var mockDao: PokemonDao
    private lateinit var mockClient: PokemonClient

    @Before
    fun before() {
        mockDao = mock()
        mockClient = mock()
        startKoin {
            modules(
                    listOf(
                            modulesDomain,
                            modulesDataSource,
                            module(override = true) {
                                single(named(CLIENT_POKEMON_TAG)) { mockClient }
                                single(named(DAO_POKEMON_TAG)) { mockDao }
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
    fun testToVerifyCallDao() {
        runBlocking {
            repository.getPokemonListFlow()
            verify(mockDao).pokemonListFlow()
        }
    }

}