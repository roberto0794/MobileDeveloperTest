package com.example.mobiledevelopertest.ui.pokemon

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiledevelopertest.R
import com.example.mobiledevelopertest.viewmodel.pokemon.PokemonViewModel
import com.example.mobiledevelopertest.databinding.ActivityPokemonBinding
import com.example.mobiledevelopertest.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class PokemonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonBinding
    private val viewModel: PokemonViewModel by viewModels()
    private val adapter = PokemonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Pokemon's"
        val layoutManager = LinearLayoutManager(this)
        binding.rvPokemon.layoutManager = layoutManager
        binding.rvPokemon.adapter = adapter

        showMessage()

        viewModel.pokemonList.observe(this){list ->

            adapter.submitList(list)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                adapter.showLoading()
            } else {
                adapter.hideLoading()
            }
        }

        binding.rvPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int){
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisible = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisible) >= totalItemCount && firstVisible >= 0) {
                    viewModel.loadPokemonList()
                }
            }
        })
        viewModel.loadPokemonList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_layout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_logout -> {
                showLogoutConfirmation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showMessage(){
        val snackbar = Snackbar.make(binding.root, "¡Bienvenido!", Snackbar.LENGTH_LONG)
        val backgroundColor = ContextCompat.getColor(this, R.color.green)
        snackbar.view.setBackgroundColor(backgroundColor)
        snackbar.show()
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("")
            .setMessage("¿Estás seguro que quieres cerrar sesión?")
            .setPositiveButton("Sí") { dialog, _ ->
                logout()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logout() {

        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}