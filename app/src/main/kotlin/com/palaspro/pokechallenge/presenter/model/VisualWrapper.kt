package com.palaspro.pokechallenge.presenter.model

import com.palaspro.pokechallenge.datasource.model.PokemonEntity

/**
 * Translate a list of [PokemonEntity] to a list of [PokemonVo]
 */
fun List<PokemonEntity>.toListItems(hasLoadMore: Boolean): List<ListItem<PokemonVo>> {
    val listItems = ArrayList<ListItem<PokemonVo>>()
    forEach {
        listItems.add(ListItem(ListItem.TYPE_ITEM, it.toVisualObject()))
    }
    if (hasLoadMore) {
        listItems.add(ListItem(ListItem.TYPE_LOAD_MORE))
    }
    return listItems
}

/**
 * Translate a [PokemonEntity] to a [PokemonVo]
 */
fun PokemonEntity.toVisualObject(): PokemonVo =
        PokemonVo(
                id,
                name,
                urlImage,
                order,
                weight,
                height,
                baseExperience,
                abilities = abilities.filter { it.isNotEmpty() },
                moves = moves.filter { it.isNotEmpty() },
                types = types.filter { it.isNotEmpty() })