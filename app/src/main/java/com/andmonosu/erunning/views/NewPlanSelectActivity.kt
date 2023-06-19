package com.andmonosu.erunning.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.andmonosu.erunning.R

class NewPlanSelectActivity : AppCompatActivity() {

    private lateinit var btnNewCustomPlan: Button
    private lateinit var btnPlanByQuestionary:Button
    private lateinit var email:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_plan_select)
        email = intent.extras?.getString("email").toString()
        btnNewCustomPlan = findViewById(R.id.btnNewCustomPlan)
        btnPlanByQuestionary = findViewById(R.id.btnPlanByQuestionary)
        btnNewCustomPlan.setOnClickListener {
            val intent = Intent(this, NewPlanConfigurationActivity::class.java).apply {
                putExtra("email",  email)
            }
            startActivity(intent)
        }

        btnPlanByQuestionary.setOnClickListener {
            val intent = Intent(this, PlanByQuestionsActivity::class.java).apply {
                putExtra("email",  email)
            }
            startActivity(intent)
        }
    }
}