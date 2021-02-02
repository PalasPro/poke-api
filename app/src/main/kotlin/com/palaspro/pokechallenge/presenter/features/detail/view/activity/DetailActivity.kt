package com.palaspro.pokechallenge.presenter.features.detail.view.activity

import android.os.Bundle
import android.view.MenuItem
import coil.load
import com.palaspro.pokechallenge.base.BaseActivity
import com.palaspro.pokechallenge.databinding.ActivityDetailBinding
import com.palaspro.pokechallenge.domain.model.Constants
import com.palaspro.pokechallenge.presenter.features.detail.viewmodel.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : BaseActivity() {

    private val viewModelDetail : DetailViewModel by viewModel() {
        parametersOf(intent.getIntExtra(Constants.Extras.EXTRA_ID, -1))
    }
    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()

        setupObservers()

        viewModelDetail.onCreateActivity()
    }

    private fun setupObservers() {
        viewModelDetail.pokemonDetail.observe(this) { pokemon ->
            pokemon?.let {
                binding.detailPokeImage.load(pokemon.urlImage)
                binding.detailPokeName.text = pokemon.name
                // TODO
            }
        }
    }

    private fun setupViews() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

}