package com.palaspro.pokechallenge.presenter.features.detail.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.palaspro.pokechallenge.databinding.ItemStringBinding
import com.palaspro.pokechallenge.presenter.features.detail.view.viewholder.StringViewHolder

class ListStringAdapter : RecyclerView.Adapter<StringViewHolder>() {

    var items : List<String> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            StringViewHolder(ItemStringBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.binding.dataItem.text = items[position]
    }

    override fun getItemCount(): Int = items.size
}