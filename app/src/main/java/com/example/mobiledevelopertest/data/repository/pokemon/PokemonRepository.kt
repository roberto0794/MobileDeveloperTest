package com.example.mobiledevelopertest.data.repository.pokemon

import com.example.mobiledevelopertest.data.model.pokemon.PokemonDetailModel
import com.example.mobiledevelopertest.data.model.pokemon.PokemonModel
import com.example.mobiledevelopertest.data.network.RetrofitClient

class PokemonRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonModel>? {
        val response = apiService.getPokemonList(limit, offset)
        return if(response.isSuccessful) response.body()?.results else null
    }

    suspend fun getPokemonDetail(id: String): PokemonDetailModel? {

        val response = apiService.getPokemonDetail(id)
        return if(response.isSuccessful) response.body() else null
    }
}