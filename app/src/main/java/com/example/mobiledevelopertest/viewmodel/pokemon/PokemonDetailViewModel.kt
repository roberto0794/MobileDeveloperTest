package com.example.mobiledevelopertest.viewmodel.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiledevelopertest.data.model.pokemon.PokemonDetailModel
import com.example.mobiledevelopertest.data.repository.pokemon.PokemonRepository
import kotlinx.coroutines.launch

class PokemonDetailViewModel: ViewModel() {

    private val repository = PokemonRepository()
    private val _pokemonDetail = MutableLiveData<PokemonDetailModel>()
    val pokemonDetail: LiveData<PokemonDetailModel> = _pokemonDetail

    fun loadPokemonDetail(id: String){

        viewModelScope.launch {

            val detail = repository.getPokemonDetail(id)
            detail?.let {
                _pokemonDetail.value = it
            }
        }
    }
}