package com.example.mobiledevelopertest.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobiledevelopertest.R
import com.example.mobiledevelopertest.ui.pokemon.PokemonActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar =findViewById<ProgressBar>(R.id.progressBar)
        login()
        checkActiveSession()
    }

    fun login(){

        auth = FirebaseAuth.getInstance()
        val txtEmail = findViewById<EditText>(R.id.editTextEmail)
        val txtPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.buttonLogin)

        btnLogin.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            progressBar.visibility = View.GONE
                            startActivity(Intent(this, PokemonActivity::class.java))
                            finish()
                        }
                        else{
                            progressBar.visibility = View.GONE
                            Toast.makeText(this, "No fue posible iniciar sesión", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Ingrese sus credenciales de acceso", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkActiveSession(){

        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            startActivity(Intent(this, PokemonActivity::class.java))
            finish()
            return
        }
    }
}