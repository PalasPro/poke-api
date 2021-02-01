package com.palaspro.pokechallenge.feature.detail.view

import android.os.Bundle
import com.palaspro.pokechallenge.base.BaseActivity
import com.palaspro.pokechallenge.databinding.ActivityDetailBinding
import com.palaspro.pokechallenge.feature.detail.viewmodel.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity() {

    private val viewmodel : DetailViewModel by viewModel()
    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}