package com.palaspro.pokechallenge.presenter.feature.main.view.activity

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.palaspro.pokechallenge.R
import com.palaspro.pokechallenge.base.BaseActivity
import com.palaspro.pokechallenge.databinding.ActivityMainBinding
import com.palaspro.pokechallenge.presenter.feature.main.view.adapter.PokemonAdapter
import com.palaspro.pokechallenge.presenter.feature.main.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModelMain: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private val adapter = PokemonAdapter()
    private val listenerAdapter = object : PokemonAdapter.OnPokemonAdapterListener {

        override fun onPokemonSelected(id: Int) {
            viewModelMain.navigateToDetail(id)
        }
        override fun loadMore() {
            viewModelMain.loadMorePokemon()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelMain.onCreateActivity()

        setupViews()

        setupObservers()
    }

    private fun setupObservers() {
        viewModelMain.pokemonList.observe(this) { pokemonList ->
            adapter.pokemons = pokemonList
        }
        viewModelMain.loading.observe(this) { isLoading ->
            binding.mainSwipeRefresh.isRefreshing = isLoading
        }
    }

    private fun setupViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)

        binding.mainSwipeRefresh.setOnRefreshListener {
            viewModelMain.resetLoadingPokemon()
        }
        adapter.listener = listenerAdapter
        binding.mainRecycler.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.count_colums))
        binding.mainRecycler.adapter = adapter
    }

}