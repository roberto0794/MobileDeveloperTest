package com.example.mobiledevelopertest.data.model.pokemon

data class PokemonDetailModel(

    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>,
    val sprites: Sprites
)

data class TypeSlot(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String
)

data class Sprites(
    val front_default: String
)
