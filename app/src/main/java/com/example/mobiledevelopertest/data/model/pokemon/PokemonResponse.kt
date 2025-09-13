package com.example.mobiledevelopertest.data.model.pokemon

data class PokemonResponse(

    val count: Int,
    val next: String,
    val previous: String,
    val results: List<PokemonModel> = emptyList()
)