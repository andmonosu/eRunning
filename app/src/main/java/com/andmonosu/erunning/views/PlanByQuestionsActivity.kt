package com.andmonosu.erunning.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.andmonosu.erunning.R
import com.andmonosu.erunning.data.model.Training
import com.andmonosu.erunning.data.model.TrainingProvider
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.CollectionReference
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
                    val plansRef = db.collection("users").document(email?:"").collection("plans")
                    val training = Training(TrainingProvider.plan5km8weeks, isActive = false)
                    savePlan(plansRef,training)


                }else if(firstClicked==2){
                    db.collection("users").document(email?:"").collection("plans").document("1")
                        .set(hashMapOf("distance" to answer1, "duration" to btnOpt1.text.toString())).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()

                            }else if(task.isCanceled) {
                                Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }else if(firstClicked==3){
                    db.collection("users").document(email?:"").collection("plans").document("1")
                        .set(hashMapOf("distance" to answer1, "duration" to "12 semanas", "objective" to btnOpt1.text.toString())).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                    putExtra("email", email)
                                })
                            }else if(task.isCanceled) {
                                Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }else if(firstClicked==4){
                    answer2 = btnOpt4.text.toString()
                    btnOpt1.text = "3 horas"
                    btnOpt2.text = "3:30 horas"
                    btnOpt3.text = "4 horas"
                    btnOpt4.text = "4:30 horas"
                    secondClicked = 1
                }
            }else if(answer3.isEmpty()){
                db.collection("users").document(email?:"").collection("plans").document("1")
                    .set(hashMapOf("distance" to answer1, "duration" to answer2, "objective" to btnOpt1.text.toString())).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                putExtra("email",  email)
                            })
                        }else if(task.isCanceled) {
                            Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
        btnOpt2.setOnClickListener {
            if(answer1.isEmpty()){
                answer1 = btnOpt2.text.toString()
                btnOpt1.text = "6 semanas"
                btnOpt2.text = "8 semanas"
                btnOpt3.text = "10 semanas"
                btnOpt4.text = "11 semanas"
                firstClicked=2
            }else if(answer2.isEmpty()){
                if(firstClicked==1){
                    db.collection("users").document(email?:"").collection("plans").document("1")
                        .set(hashMapOf("distance" to answer1, "duration" to btnOpt2.text.toString())).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                    putExtra("email",  email)
                                })
                            }else if(task.isCanceled) {
                                Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }else if(firstClicked==2){
                    db.collection("users").document(email?:"").collection("plans").document("1")
                        .set(hashMapOf("distance" to answer1, "duration" to btnOpt2.text.toString())).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                    putExtra("email",  email)
                                })
                            }else if(task.isCanceled) {
                                Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }else if(firstClicked==3){
                    db.collection("users").document(email?:"").collection("plans").document("1")
                        .set(hashMapOf("distance" to answer1, "duration" to "12 semanas", "objective" to btnOpt2.text.toString())).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                    putExtra("email",  email)
                                })
                            }else if(task.isCanceled) {
                                Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }else if(firstClicked==4){
                    db.collection("users").document(email?:"").collection("plans").document("1")
                        .set(hashMapOf("distance" to answer1, "duration" to btnOpt2.text.toString())).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                    putExtra("email",  email)
                                })
                            }else if(task.isCanceled) {
                                Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }else if(answer3.isEmpty()){
                db.collection("users").document(email?:"").collection("plans").document("1")
                    .set(hashMapOf("distance" to answer1, "duration" to answer2, "objective" to btnOpt2.text.toString())).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                putExtra("email",  email)
                            })
                        }else if(task.isCanceled) {
                            Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
        btnOpt3.setOnClickListener {
            if(answer1.isEmpty()){
                answer1 = btnOpt3.text.toString()
                btnOpt1.text = "Menos de 1:30h"
                btnOpt2.text = "Menos de 2h"
                btnOpt3.isVisible = false
                btnOpt4.isVisible = false
                firstClicked=3
            }else if(answer2.isEmpty()){
                if(firstClicked==1){
                    db.collection("users").document(email?:"").collection("plans").document("1")
                        .set(hashMapOf("distance" to answer1, "duration" to btnOpt3.text.toString())).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                    putExtra("email", email)
                                })
                            }else if(task.isCanceled) {
                                Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }else if(firstClicked==2){
                    db.collection("users").document(email?:"").collection("plans").document("1")
                        .set(hashMapOf("distance" to answer1, "duration" to btnOpt3.text.toString())).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                    putExtra("email",  email)
                                })
                            }else if(task.isCanceled) {
                                Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }else if(firstClicked==4){
                    db.collection("users").document(email?:"").collection("plans").document("1")
                        .set(hashMapOf("distance" to answer1, "duration" to btnOpt3.text.toString())).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                    putExtra("email",  email)
                                })
                            }else if(task.isCanceled) {
                                Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }else if(answer3.isEmpty()){
                db.collection("users").document(email?:"").collection("plans").document("1")
                    .set(hashMapOf("distance" to answer1, "duration" to answer2, "objective" to btnOpt3.text.toString())).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                putExtra("email",  email)
                            })
                        }else if(task.isCanceled) {
                            Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
        btnOpt4.setOnClickListener {
            if(answer1.isEmpty()){
                answer1 = btnOpt4.text.toString()
                btnOpt1.text = "12 semanas"
                btnOpt2.text = "16 semanas"
                btnOpt3.text = "20 semanas"
                btnOpt4.isVisible = false
                firstClicked=4
            }else if(answer2.isEmpty()){
                if(firstClicked==2){
                    answer2 = btnOpt4.text.toString()
                    btnOpt1.text = "40 minutos"
                    btnOpt2.text = "45 minutos"
                    btnOpt3.text = "50 minutos"
                    btnOpt4.text = "55 minutos"
                    secondClicked = 4
                }
            }else if(answer3.isEmpty()){
                db.collection("users").document(email?:"").collection("plans").document("1")
                    .set(hashMapOf("distance" to answer1, "duration" to answer2, "objective" to btnOpt4.text.toString())).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MyPlansActivity::class.java).apply {
                                putExtra("email",  email)
                            })
                        }else if(task.isCanceled) {
                            Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
    }

    private fun savePlan(plansRef:CollectionReference , training: Training){
        plansRef.count().get(AggregateSource.SERVER).addOnCompleteListener {count->
            val elements = count.result.count+1
            training.name = "Plan ${elements}"
            val plan =  plansRef.document((elements).toString())
            plan.set(hashMapOf("distance" to answer1, "duration" to btnOpt1.text.toString(), "title" to training.name, "isActive" to training.isActive)).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                    var i = 0
                    while (i<training.trainingWeeks.size){
                        val week = training.trainingWeeks[i]
                        val weekRef =  plan.collection("weeks").document((i+1).toString())
                        weekRef.set(
                            hashMapOf("title" to week.name)
                        )
                        for(day in week.days){
                            weekRef.collection("days").document(day.day.toString()).set(
                                hashMapOf("type" to day.type, "time" to day.time, "distance" to day.distance, "pace" to day.pace)
                            )
                        }
                        i++
                    }
                    startActivity(Intent(this, MyPlansActivity::class.java).apply {
                        putExtra("email",  email)
                    })
                }else if(task.isCanceled) {
                    Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                        .show()
                }
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