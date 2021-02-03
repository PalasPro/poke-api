package com.palaspro.pokechallenge.presenter.features.detail.view.activity

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.palaspro.pokechallenge.R
import com.palaspro.pokechallenge.presenter.base.BaseActivity
import com.palaspro.pokechallenge.databinding.ActivityDetailBinding
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.presenter.features.detail.view.adapter.ListStringAdapter
import com.palaspro.pokechallenge.presenter.features.detail.viewmodel.DetailViewModel
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : BaseActivity() {

    private val viewModelDetail: DetailViewModel by viewModel() {
        parametersOf(intent.getIntExtra(Constants.Extras.EXTRA_ID, -1))
    }

    private lateinit var binding: ActivityDetailBinding

    private val adapterAbilities = ListStringAdapter()
    private val adapterMoves = ListStringAdapter()
    private val adapterTypes = ListStringAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()

        setupObservers()

        viewModelDetail.onCreateActivity()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> {
                    super.onOptionsItemSelected(item)
                }
            }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModelDetail.loading.collect { isLoading ->
                binding.detailSwipeRefresh.isRefreshing = isLoading
                binding.detailPokeFavorite.isEnabled = !isLoading
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModelDetail.pokemonDetail.collect { pokemon ->
                pokemon?.let {
                    binding.detailPokeImage.load(pokemon.urlImage)
                    binding.detailPokeName.text = pokemon.name
                    binding.detailPokeOrder.text = getString(R.string.detail_order, pokemon.order)
                    binding.detailWeightData.text = getString(R.string.detail_weight, pokemon.weight)
                    binding.detailHeightData.text = getString(R.string.detail_height, pokemon.height)
                    binding.detailExperienceData.text = "${pokemon.baseExperience}"

                    adapterAbilities.items = pokemon.abilities
                    adapterMoves.items = pokemon.moves
                    adapterTypes.items = pokemon.types

                    binding.detailPokeFavorite.isEnabled = true
                    binding.detailPokeFavorite.visibility = View.VISIBLE
                    if(pokemon.isFavorite) {
                        binding.detailPokeFavorite.setImageResource(R.drawable.ic_favorite_on)
                    } else {
                        binding.detailPokeFavorite.setImageResource(R.drawable.ic_favorite_off)
                    }
                }
            }
        }
    }

    private fun setupViews() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar)

        binding.detailAbilitiesLists.layoutManager = LinearLayoutManager(this)
        binding.detailAbilitiesLists.adapter = adapterAbilities
        binding.detailMoveLists.layoutManager = LinearLayoutManager(this)
        binding.detailMoveLists.adapter = adapterMoves
        binding.detailTypeList.layoutManager = LinearLayoutManager(this)
        binding.detailTypeList.adapter = adapterTypes

        binding.detailSwipeRefresh.setOnRefreshListener { viewModelDetail.loadPokemonDetail() }

        binding.detailPokeFavorite.setOnClickListener { viewModelDetail.changeFavoriteStatus() }
    }


}