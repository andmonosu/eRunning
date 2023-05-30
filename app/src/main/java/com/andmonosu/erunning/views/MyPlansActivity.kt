package com.andmonosu.erunning.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.andmonosu.erunning.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyPlansActivity : AppCompatActivity() {

    private lateinit var btnSession: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_plans)

        initComponents()
        initListeners()
    }

    private fun initListeners() {
        btnSession.setOnClickListener {
            val intent = Intent(this, PlanByQuestionsActivity::class.java).apply {
                putExtra("email",  extras?.getString("email"))
            }
            startActivity(intent)
        }
    }

    private fun initComponents() {
        btnSession = findViewById(R.id.btnSession)
    }
}