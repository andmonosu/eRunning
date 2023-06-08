package com.andmonosu.erunning.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.andmonosu.erunning.MainActivity
import com.andmonosu.erunning.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyPlansActivity : AppCompatActivity() {

    private lateinit var btnSession: FloatingActionButton
    private lateinit var imgBtnBack: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_plans)

        initComponents()
        initListeners()
    }

    private fun initListeners() {
        val email = intent.extras?.getString("email")
        btnSession.setOnClickListener {
            val intent = Intent(this, PlanByQuestionsActivity::class.java).apply {
                    putExtra("email",  email)
            }
            startActivity(intent)
        }

        imgBtnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("email",  email)
            }
            startActivity(intent)
        }
    }

    private fun initComponents() {
        btnSession = findViewById(R.id.btnSession)
        imgBtnBack = findViewById(R.id.imgBtnBack)
    }
}