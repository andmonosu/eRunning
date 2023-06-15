package com.andmonosu.erunning.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andmonosu.erunning.MainActivity
import com.andmonosu.erunning.R
import com.andmonosu.erunning.adapter.TrainingAdapter
import com.andmonosu.erunning.models.SessionType
import com.andmonosu.erunning.models.Training
import com.andmonosu.erunning.models.TrainingDay
import com.andmonosu.erunning.models.TrainingProvider
import com.andmonosu.erunning.models.TrainingWeek
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MyPlansActivity : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var email:String
    private lateinit var btnSession: FloatingActionButton
    private lateinit var imgBtnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_plans)
        email = intent.extras?.getString("email").toString()
        initRecyclerView()
        initComponents()
        initListeners()
    }

    private fun initRecyclerView(){
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        val recyclerView = findViewById<RecyclerView>(R.id.rvPlans)
        recyclerView.layoutManager = manager
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val plans = getTrainings()
                Log.i("planes", plans.toString())
                recyclerView.adapter = TrainingAdapter(plans) {
                    onItemSelected(
                        it
                    )
                }
                recyclerView.addItemDecoration(decoration)

            } catch (exception: Exception) {
              Log.e("Error", exception.message.toString())
            }
        }
    }

    private suspend fun getTrainings(): List<Training> = suspendCoroutine { continuation ->
        val plansList = mutableListOf<Training>()
        db.collection("users").document(email).collection("plans").get().addOnSuccessListener { documents ->
            val getPlansTasks = mutableListOf<Task<QuerySnapshot>>()
            for (plan in documents) {
                val training = Training(name = plan.data["title"].toString(), isActive = plan.data["isActive"] as Boolean)
                val weeksDocs = plan.reference.collection("weeks").get()
                getPlansTasks.add(weeksDocs)
                weeksDocs.addOnSuccessListener { weeks ->
                    val getWeeksTasks = mutableListOf<Task<QuerySnapshot>>()
                    for (weekDoc in weeks) {
                        val week = TrainingWeek(name = weekDoc.data["title"].toString())
                        val daysDocs = weekDoc.reference.collection("days").get()
                        getWeeksTasks.add(daysDocs)
                        daysDocs.addOnSuccessListener { days ->
                            for (dayDoc in days) {
                                val day = TrainingDay(
                                    DayOfWeek.valueOf(dayDoc.id),
                                    SessionType.valueOf(dayDoc.data["type"].toString()),
                                    dayDoc.data["time"] as Number,
                                    dayDoc.data["distance"] as Double,
                                    dayDoc.data["pace"] as Double
                                )
                                week.days.add(day)
                            }
                        }.addOnFailureListener { exception ->
                            continuation.resumeWith(Result.failure(exception))
                            return@addOnFailureListener
                        }
                        training.trainingWeeks.add(week)
                    }
                    Tasks.whenAllSuccess<QuerySnapshot>(getWeeksTasks)
                        .addOnSuccessListener {
                            plansList.add(training)
                            if (plansList.size == documents.size()) {
                                continuation.resume(plansList)
                            }
                        }
                        .addOnFailureListener { exception ->
                            continuation.resumeWith(Result.failure(exception))
                        }
                }.addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
            }

            Tasks.whenAllSuccess<QuerySnapshot>(getPlansTasks)
                .addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }.addOnFailureListener { exception ->
            continuation.resumeWith(Result.failure(exception))
        }
    }

    private fun onItemSelected(training:Training){
        Toast.makeText(this,training.name, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, PlanDetailsActivity::class.java)
        intent.putExtra("training", training)
        intent.putExtra("email",email)
        startActivity(intent)
    }

    private fun initListeners() {
        btnSession.setOnClickListener {
            val intent = Intent(this, NewPlanSelectActivity::class.java).apply {
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