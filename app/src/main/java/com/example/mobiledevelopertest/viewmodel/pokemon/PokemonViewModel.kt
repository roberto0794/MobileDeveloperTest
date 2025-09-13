package com.example.mobiledevelopertest.viewmodel.pokemon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiledevelopertest.data.model.pokemon.PokemonModel
import com.example.mobiledevelopertest.data.repository.pokemon.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    private val repository = PokemonRepository()
    private val _pokemonList = MutableLiveData<List<PokemonModel>>(emptyList())
    val pokemonList: LiveData<List<PokemonModel>> = _pokemonList

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var offset = 0
    private val limit = 20
    private var isLastPage = false

    fun loadPokemonList() {
        if (_isLoading.value == true || isLastPage) return

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val newList = repository.getPokemonList(limit, offset)
                if (newList != null) {
                    if (newList.isEmpty()) {
                        isLastPage = true
                    } else {
                        offset += limit
                        _pokemonList.value = _pokemonList.value!! + newList
                    }
                }
            } catch (e: Exception) {
                Log.e("VMPokemon", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}