package com.example.mobiledevelopertest.ui.pokemon

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobiledevelopertest.data.model.pokemon.PokemonModel
import com.example.mobiledevelopertest.databinding.ItemLoadingBinding
import com.example.mobiledevelopertest.databinding.ItemPokemonBinding
import com.example.mobiledevelopertest.ui.pokemon.PokemonDetailActivity.Companion.POKEMON_ID
import com.example.mobiledevelopertest.ui.pokemon.PokemonDetailActivity.Companion.POKEMON_IMAGE

class PokemonAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val list = mutableListOf<PokemonModel?>()
    private var isLoadingAdded = false

    fun submitList(newList: List<PokemonModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun showLoading() {
        if (!isLoadingAdded) {
            list.add(null)
            notifyItemInserted(list.size - 1)
            isLoadingAdded = true
        }
    }

    fun hideLoading() {
        if (isLoadingAdded && list.isNotEmpty()) {
            val lastIndex = list.size - 1
            if (list[lastIndex] == null) {
                list.removeAt(lastIndex)
                notifyItemRemoved(lastIndex)
                isLoadingAdded = false
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    inner class PokemonViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)
    inner class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PokemonViewHolder(binding)
        }
        else
        {
            val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is PokemonViewHolder){
            val pokemon = list[position]
            val getId = pokemon?.url?.trimEnd('/')?.substringAfterLast('/')
            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$getId.png"

            holder.binding.txtPokemonName.text = pokemon?.name?.replaceFirstChar { it.uppercase() }

            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .into(holder.binding.imgPokemon)

            holder.itemView.setOnClickListener {
                val intent = Intent(it.context, PokemonDetailActivity::class.java)
                intent.putExtra(POKEMON_ID, pokemon?.name)
                intent.putExtra(POKEMON_IMAGE, imageUrl)
                it.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {

        return list.size
    }
}