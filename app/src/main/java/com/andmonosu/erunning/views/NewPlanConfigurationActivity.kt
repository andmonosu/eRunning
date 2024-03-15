package com.andmonosu.erunning.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.andmonosu.erunning.R
import com.andmonosu.erunning.data.model.SessionType
import com.andmonosu.erunning.data.model.Training
import com.andmonosu.erunning.data.model.TrainingDay
import com.andmonosu.erunning.data.model.TrainingWeek
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.DayOfWeek

class NewPlanConfigurationActivity : AppCompatActivity() {

    private var currentWeeks:Int = 10
    private lateinit var btnSubtractWeek: FloatingActionButton
    private lateinit var btnAddWeek: FloatingActionButton
    private lateinit var tvWeeks: TextView
    private lateinit var btnCreatePlan: Button
    private lateinit var  etNewPlanName: EditText
    private lateinit var email:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_plan_configuration)
        email = intent.extras?.getString("email").toString()
        initComponents()
        initListeners()
        initUI()
    }

    private fun initUI() {
        setWeeks()
    }

    private fun initListeners() {
        btnAddWeek.setOnClickListener{
            currentWeeks += 1
            setWeeks()
        }
        btnSubtractWeek.setOnClickListener{
            currentWeeks -= 1
            setWeeks()
        }
        btnCreatePlan.setOnClickListener{
            val training = Training(name = etNewPlanName.text.toString() , isActive = false)
            var i = 1
            while(i<=currentWeeks){
                val week = TrainingWeek(mutableListOf(
                    TrainingDay(DayOfWeek.MONDAY,SessionType.CC),
                    TrainingDay(DayOfWeek.TUESDAY,SessionType.CC),
                    TrainingDay(DayOfWeek.WEDNESDAY,SessionType.CC),
                    TrainingDay(DayOfWeek.THURSDAY,SessionType.CC),
                    TrainingDay(DayOfWeek.FRIDAY,SessionType.CC),
                    TrainingDay(DayOfWeek.SATURDAY,SessionType.CC),
                    TrainingDay(DayOfWeek.SUNDAY,SessionType.CC),
                ),"$i")
                training.trainingWeeks.add(week)
                i++
            }
            val intent = Intent(this, PlanDetailsActivity::class.java)
            intent.putExtra("training", training)
            intent.putExtra("email",email)
            intent.putExtra("isCreating",true)
            startActivity(intent)
        }
    }

    private fun setWeeks() {
        tvWeeks.text = currentWeeks.toString()
    }

    private fun initComponents() {
        btnSubtractWeek = findViewById(R.id.btnSubtractWeek)
        btnAddWeek = findViewById(R.id.btnAddWeek)
        tvWeeks = findViewById(R.id.tvWeeks)
        btnCreatePlan = findViewById(R.id.btnCreatePlan)
        etNewPlanName = findViewById(R.id.etNewPlanName)
    }
}