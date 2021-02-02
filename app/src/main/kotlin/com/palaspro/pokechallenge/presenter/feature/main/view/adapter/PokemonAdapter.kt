package com.palaspro.pokechallenge.presenter.feature.main.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.palaspro.pokechallenge.databinding.ItemLoadmoreBinding
import com.palaspro.pokechallenge.databinding.ItemPokemonBinding
import com.palaspro.pokechallenge.presenter.feature.main.view.viewholder.LoadMoreViewHolder
import com.palaspro.pokechallenge.presenter.feature.main.view.viewholder.PokemonViewHolder
import com.palaspro.pokechallenge.presenter.model.ListItem
import com.palaspro.pokechallenge.presenter.model.PokemonVo

class PokemonAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnPokemonAdapterListener {
        fun onPokemonSelected(id: Int)
        fun loadMore()
    }

    var listener: OnPokemonAdapterListener? = null
    var pokemons: List<ListItem<PokemonVo>> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                ListItem.TYPE_LOAD_MORE -> LoadMoreViewHolder(ItemLoadmoreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                else -> PokemonViewHolder(ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PokemonViewHolder -> {
                pokemons[position].item?.let { pokemonVo ->
                    holder.binding.itemPokeName.text = pokemonVo.name

                    if(holder.binding.itemPokeImage.tag != pokemonVo.id) {
                        holder.binding.itemPokeImage.setImageDrawable(null)
                        holder.binding.itemPokeImage.tag = pokemonVo.id
                    }
                    pokemonVo.urlImage?.let {  urlImage ->
                        holder.binding.itemPokeImage.load(urlImage)
                    }

                    holder.binding.root.setOnClickListener {
                        listener?.onPokemonSelected(pokemonVo.id)
                    }
                }
            }
            is LoadMoreViewHolder -> {
                listener?.loadMore()
            }
        }
    }

    override fun getItemViewType(position: Int): Int = pokemons[position].type

    override fun getItemCount(): Int = pokemons.size
}