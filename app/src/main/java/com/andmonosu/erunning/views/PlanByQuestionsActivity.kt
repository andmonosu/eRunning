package com.andmonosu.erunning.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.view.isVisible
import com.andmonosu.erunning.R

class PlanByQuestionsActivity : AppCompatActivity() {

    private var answer1: String = ""
    private var answer2: String = ""
    private var answer3: String = ""
    private lateinit var firstClicked: Number
    private lateinit var secondClicked: Number
    private lateinit var btnOpt1: Button
    private lateinit var btnOpt2: Button
    private lateinit var btnOpt3: Button
    private lateinit var btnOpt4: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_by_questions)
        initComponents()
        initListeners()
        initUI()
    }

    private fun initUI() {

    }

    private fun initListeners() {
        btnOpt1.setOnClickListener {
            if(answer1.isEmpty()){
                answer1 = btnOpt1.text.toString()
                btnOpt1.text = "8 semanas"
                btnOpt2.text = "10 semanas"
                btnOpt3.text = "12 semanas"
                btnOpt4.isVisible = false
                firstClicked=1
            }else if(answer2.isEmpty()){
                if(firstClicked==1){

                }else if(firstClicked==2){

                }else if(firstClicked==3){

                }else if(firstClicked==4){

                }
            }else if(answer3.isEmpty()){

            }
        }
        btnOpt2.setOnClickListener {
            if(answer1.isEmpty()){
                answer1 = btnOpt2.text.toString()
                firstClicked=2
            }else if(answer2.isEmpty()){
                if(firstClicked==1){

                }else if(firstClicked==2){

                }else if(firstClicked==3){

                }else if(firstClicked==4){

                }
            }else if(answer3.isEmpty()){

            }
        }
        btnOpt3.setOnClickListener {
            if(answer1.isEmpty()){
                answer1 = btnOpt3.text.toString()
                firstClicked=3
            }else if(answer2.isEmpty()){
                if(firstClicked==1){

                }else if(firstClicked==2){

                }else if(firstClicked==3){

                }else if(firstClicked==4){

                }
            }else if(answer3.isEmpty()){

            }
        }
        btnOpt4.setOnClickListener {
            if(answer1.isEmpty()){
                answer1 = btnOpt4.text.toString()
                firstClicked=4
            }else if(answer2.isEmpty()){

            }else if(answer3.isEmpty()){

            }
        }
    }

    private fun initComponents() {
        btnOpt1 = findViewById(R.id.btnOpt1)
        btnOpt2 = findViewById(R.id.btnOpt2)
        btnOpt3 = findViewById(R.id.btnOpt3)
        btnOpt4 = findViewById(R.id.btnOpt4)
    }
}