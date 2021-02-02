package com.palaspro.pokechallenge.presenter.model


data class ListItem<T>(val type : Int, val item : T? = null) {
    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LOAD_MORE = 1
    }
}

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