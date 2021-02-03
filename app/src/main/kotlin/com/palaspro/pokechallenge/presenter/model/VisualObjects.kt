package com.palaspro.pokechallenge.presenter.model

/**
 * Class to use in the visual list, like a recycler view.
 * @param [type] is the type of item
 * @param [item] is the data of the item. It can be null
 */
data class ListItem<T>(val type : Int, val item : T? = null) {
    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LOAD_MORE = 1
    }
}

/**
 * Class that represent a Pokemon Visual Object
 */
data class PokemonVo(val id: Int,
                     val name: String,
                     val urlImage : String? = null,
                     val order : Int = 0,
                     val weight : Int = 0,
                     val height : Int = 0,
                     val baseExperience : Int = 0,
                     val abilities: List<String> = listOf(),
                     val moves: List<String> = listOf(),
                     val types :List<String> = listOf())