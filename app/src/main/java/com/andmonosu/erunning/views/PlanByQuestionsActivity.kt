package com.andmonosu.erunning.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import com.andmonosu.erunning.R
import com.google.firebase.firestore.FirebaseFirestore

class PlanByQuestionsActivity : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var email:String
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
        email = intent.extras?.getString("email").toString()
        initComponents()
        initListeners()
        initUI()
    }

    private fun initUI() {
        btnOpt1.text = "5 km"
        btnOpt2.text = "10 km"
        btnOpt3.text = "Media maratón (21 km)"
        btnOpt4.text = "Una maratón (42 km)"
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
                    db.collection("users").document(email?:"").collection("plans").document("1")
                        .set(hashMapOf("distance" to answer1, "duration" to btnOpt2.text.toString())).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                putExtra("email",  extras?.getString("email"))
                            })
                        }else if(task.isCanceled) {
                            Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
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
                    db.collection("users").document(email?:"").collection("plans").document("1")
                        .set(hashMapOf("distance" to answer1, "duration" to btnOpt2.text.toString())).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                    putExtra("email",  extras?.getString("email"))
                                })
                            }else if(task.isCanceled) {
                                Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
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
                    db.collection("users").document(email?:"").collection("plans").document("1")
                        .set(hashMapOf("distance" to answer1, "duration" to btnOpt2.text.toString())).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                    putExtra("email",  extras?.getString("email"))
                                })
                            }else if(task.isCanceled) {
                                Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
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
                if(firstClicked==2){

                }else if(firstClicked==3){

                }else if(firstClicked==4){

                }
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