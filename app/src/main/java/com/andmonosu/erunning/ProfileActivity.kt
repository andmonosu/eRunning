package com.andmonosu.erunning

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC,GOOGLE
}

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        initComponents()
        setup(email?:"",provider?:"")

        //Guardar datos

        val prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()
    }

    private lateinit var tvProvider:TextView
    private lateinit var tvEmail:TextView
    private lateinit var btnLogout: Button

    private fun initComponents() {
        tvProvider = findViewById(R.id.tvProvider)
        tvEmail = findViewById(R.id.tvEmail)
        btnLogout = findViewById(R.id.btnLogout)
    }

    private fun setup(email:String, provider:String) {
        title = "Inicio"

        tvProvider.text = provider
        tvEmail.text = email

        btnLogout.setOnClickListener{

            val prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }



    }
}