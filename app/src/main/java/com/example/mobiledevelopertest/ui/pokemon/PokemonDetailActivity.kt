package com.example.mobiledevelopertest.ui.pokemon

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.mobiledevelopertest.viewmodel.pokemon.PokemonViewModel
import com.example.mobiledevelopertest.databinding.ActivityPokemonDetailBinding
import com.example.mobiledevelopertest.viewmodel.pokemon.PokemonDetailViewModel

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding
    private val viewModel: PokemonDetailViewModel by viewModels()

    companion object {
        const val POKEMON_ID = "pokemonId"
        const val POKEMON_IMAGE = "pokemonImage"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Informacion"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pokemonId = intent.getStringExtra(POKEMON_ID) ?: return finish()
        val pokemonImage = intent.getStringExtra(POKEMON_IMAGE) ?: return finish()

        Glide.with(this)
            .load(pokemonImage)
            .into(binding.imageViewPokemon)

        viewModel.loadPokemonDetail(pokemonId)

        viewModel.pokemonDetail.observe(this){detailModel ->
            detailModel?.let{

                binding.textViewName.text = it.name.replaceFirstChar { c -> c.uppercase() }
                binding.textViewHeight.text = "Altura: ${it.height / 10.0} m"
                binding.textViewWeight.text = "Peso: ${it.weight / 10.0} kg"
                binding.textViewTypes.text = "Tipo: ${it.types.joinToString { it.type.name.capitalize() }}"
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}