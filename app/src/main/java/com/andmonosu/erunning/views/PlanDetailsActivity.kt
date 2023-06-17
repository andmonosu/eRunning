package com.andmonosu.erunning.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.andmonosu.erunning.R
import com.andmonosu.erunning.adapter.TrainingAdapter
import com.andmonosu.erunning.models.SessionType
import com.andmonosu.erunning.models.Training
import com.andmonosu.erunning.models.TrainingWeek
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_plan_details.tvDetailsDistance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import kotlin.coroutines.suspendCoroutine


class PlanDetailsActivity : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var training: Training
    private lateinit var trainingWeekSelected: TrainingWeek
    private lateinit var tvTrainingDetailsName:TextView
    private lateinit var tvMonday:TextView
    private lateinit var tvTuesday:TextView
    private lateinit var tvWednesday:TextView
    private lateinit var tvThursday:TextView
    private lateinit var tvFriday:TextView
    private lateinit var tvSaturday:TextView
    private lateinit var tvSunday:TextView
    private lateinit var cardMonday:CardView
    private lateinit var cardTuesday:CardView
    private lateinit var cardWednesday:CardView
    private lateinit var cardThursday:CardView
    private lateinit var cardFriday:CardView
    private lateinit var cardSaturday:CardView
    private lateinit var cardSunday:CardView
    private lateinit var cardDaySelected:CardView
    private lateinit var tvDaySelected:TextView
    private lateinit var spDetailsType:Spinner
    private lateinit var etDetailsTime:EditText
    private lateinit var etDetailsPace:EditText
    private lateinit var etDetailsDistance:EditText
    private lateinit var tvDetailsDistance:TextView
    private lateinit var tvDetailsPace:TextView
    private lateinit var tvDetailsTime:TextView
    private lateinit var spWeekSelect: Spinner
    private lateinit var btnEditCreatePlan:Button
    private lateinit var btnMakeActivePlan:Button

    private var isCreating:Boolean = false
    private var isEditing:Boolean = false
    private lateinit var email:String
    private var daySelected = DayOfWeek.MONDAY
    private var isChanging = false
    private lateinit var adapterType: ArrayAdapter<SessionType>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_details)
        training = intent.getParcelableExtra("training")!!
        isCreating = intent.getBooleanExtra("isCreating", false)
        email = intent.extras?.getString("email").toString()
        initComponent()
        initUI()
        initListeners()
    }

    private fun initListeners() {
        cardMonday.setOnClickListener {
            isChanging = true
            showDayInformation(DayOfWeek.MONDAY)
            changeSelectedDay(cardMonday,tvMonday)
            setType()
            isChanging = false
        }
        cardTuesday.setOnClickListener {
            isChanging = true
            changeSelectedDay(cardTuesday,tvTuesday)
            showDayInformation(DayOfWeek.TUESDAY)
            setType()
            isChanging = false
        }
        cardWednesday.setOnClickListener {
            isChanging = true
            changeSelectedDay(cardWednesday,tvWednesday)
            showDayInformation(DayOfWeek.WEDNESDAY)
            isChanging = false
        }
        cardThursday.setOnClickListener {
            isChanging = true
            changeSelectedDay(cardThursday,tvThursday)
            showDayInformation(DayOfWeek.THURSDAY)
            setType()
            isChanging = false
        }
        cardFriday.setOnClickListener {
            isChanging = true
            showDayInformation(DayOfWeek.FRIDAY)
            changeSelectedDay(cardFriday,tvFriday)
            setType()
            isChanging = false

        }
        cardSaturday.setOnClickListener {
            isChanging = true
            changeSelectedDay(cardSaturday,tvSaturday)
            showDayInformation(DayOfWeek.SATURDAY)
            setType()
            isChanging = false
        }
        cardSunday.setOnClickListener {
            isChanging = true
            changeSelectedDay(cardSunday,tvSunday)
            showDayInformation(DayOfWeek.SUNDAY)
            setType()
            isChanging = false
        }

        etDetailsTime.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if((isCreating || isEditing)&&!isChanging) {
                    for ((i, day) in trainingWeekSelected.days.withIndex()) {
                        val time = s.toString().toIntOrNull()
                        if (day.day == daySelected && time != null) {
                            trainingWeekSelected.days[i].time = time
                        }
                    }
                    changeWeekValue()
                }
            }
        })

        etDetailsDistance.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if((isCreating || isEditing)&&!isChanging) {
                    for ((i, day) in trainingWeekSelected.days.withIndex()) {
                        val distance = s.toString().toDoubleOrNull()
                        if (day.day == daySelected && distance != null) {
                            trainingWeekSelected.days[i].distance = distance
                        }
                    }
                    changeWeekValue()
                }
            }
        })
        etDetailsPace.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if((isCreating || isEditing)&&!isChanging){
                    for((i, day) in trainingWeekSelected.days.withIndex()){
                        val pace = s.toString().toDoubleOrNull()
                        if(day.day == daySelected && pace !=null){
                            trainingWeekSelected.days[i].pace = pace
                        }
                    }
                    changeWeekValue()
                }
            }
        })

        btnEditCreatePlan.setOnClickListener {
            if(!isCreating){
                if(!isEditing){
                    changeEnableET(true)
                }else{
                    val plansRef = db.collection("users").document(email).collection("plans")
                    savePlan(plansRef,training)
                    changeEnableET(false)
                }
                isEditing = !isEditing
            }else{
                val plansRef = db.collection("users").document(email).collection("plans")
                savePlan(plansRef,training)
            }
        }
        btnMakeActivePlan.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    makePlanActive()
                } catch (exception: Exception) {
                    Log.e("Error", exception.message.toString())
                }
            }
        }
    }
    
    private suspend fun makePlanActive():Void = suspendCoroutine{continuation ->
        val deleteTasks = mutableListOf<Task<Void>>()
        db.collection("users").document(email).collection("programmed sessions").get().addOnSuccessListener {documents ->
            for (document in documents){
                deleteTasks.add(document.reference.delete())
            }
        }

        Tasks.whenAllSuccess<Void>(deleteTasks).addOnSuccessListener {
            val plansRef = db.collection("users").document(email).collection("plans")
            plansRef.whereEqualTo("isActive",true).get().addOnSuccessListener {documents->
                for(document in documents){
                    val activeTraining = Training(name = document.data["title"].toString(), isActive = false)
                    plansRef.document(activeTraining.name).set(
                        hashMapOf("title" to activeTraining.name, "isActive" to activeTraining.isActive)
                    )
                }
            }.addOnSuccessListener {
                var startDate = LocalDate.now()
                while (startDate.dayOfWeek != DayOfWeek.MONDAY){
                    startDate = startDate.plusDays(1)
                }
                for(week in training.trainingWeeks.sortedBy { n -> n.name.toIntOrNull() }){
                    for(day in week.days.sortedBy { n -> n.day }){
                        db.collection("users").document(email).collection("programmed sessions").document(startDate.toString()).set(
                            hashMapOf("time" to day.time, "distance" to day.distance, "pace" to day.pace, "type" to day.type)
                        )
                        startDate = startDate.plusDays(1)
                    }
                }
                val plansRef2 = db.collection("users").document(email).collection("plans")
                training.isActive = true
                savePlan(plansRef2,training)
                btnMakeActivePlan.isVisible = false

            }
        }.addOnFailureListener { exception ->
            continuation.resumeWith(Result.failure(exception))
            return@addOnFailureListener
        }
    }

    private fun changeWeekValue(){
        for((i, week) in training.trainingWeeks.withIndex()){
            if(week.name == trainingWeekSelected.name){
                training.trainingWeeks[i] = trainingWeekSelected
            }
        }
    }

    private fun changeSelectedDay(card:CardView, tv:TextView){
        cardDaySelected.setCardBackgroundColor(ContextCompat.getColor(this,R.color.light_grey))
        tvDaySelected.setTextColor(ContextCompat.getColor(this,R.color.dark_grey))
        card.setCardBackgroundColor(ContextCompat.getColor(this,R.color.background_button))
        tv.setTextColor(ContextCompat.getColor(this,R.color.white))
        cardDaySelected = card
        tvDaySelected = tv
    }

    private fun showDayInformation(dayOfWeek:DayOfWeek){
        for (day in trainingWeekSelected.days){
            if(day.day == dayOfWeek){
                etDetailsDistance.setText(day.distance.toString())
                etDetailsPace.setText(day.pace.toString())
                etDetailsTime.setText(day.time.toString())
            }
        }
        daySelected = dayOfWeek
    }

    private fun initUI() {
        btnMakeActivePlan.isVisible = !training.isActive
        btnMakeActivePlan.text = getString(R.string.make_active)
        tvTrainingDetailsName.text = training.name
        trainingWeekSelected = training.trainingWeeks[0]
        cardMonday.setCardBackgroundColor(ContextCompat.getColor(this,R.color.background_button))
        tvMonday.setTextColor(ContextCompat.getColor(this,R.color.white))
        tvDaySelected = tvMonday
        cardDaySelected = cardMonday
        showDayInformation(DayOfWeek.MONDAY)
        val adapter = ArrayAdapter(this,R.layout.spinner_item, getWeeksNames())
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spWeekSelect.adapter = adapter
        adapterType = ArrayAdapter(this,R.layout.spinner_item_types, SessionType.values())
        adapterType.setDropDownViewResource(R.layout.spinner_item_types)
        spDetailsType.adapter = adapterType
        spDetailsType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val type = SessionType.valueOf(parent?.getItemAtPosition(pos).toString())
                for((i,day) in trainingWeekSelected.days.withIndex()){
                    if(day.day == daySelected){
                        trainingWeekSelected.days[i].type = type
                        changeVisibleET(type)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        setType()
        spWeekSelect.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val weekName = parent?.getItemAtPosition(pos).toString()
                for(week in training.trainingWeeks){
                    if(week.name == weekName.split(" ")[1]){
                        isChanging = true
                        trainingWeekSelected = week
                        showDayInformation(daySelected)
                        setType()
                        isChanging = false
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                parent?.setSelection(0)
            }
        }
        changeEnableET(isCreating)
        if(isCreating){
            btnEditCreatePlan.text = getString(R.string.create_new_plan)
        }else{
            btnEditCreatePlan.text = getString(R.string.edit_plan)
        }
    }

    private fun setType() {
        for(day in trainingWeekSelected.days){
            if (day.day == daySelected){
                spDetailsType.setSelection(adapterType.getPosition(day.type))
                changeVisibleET(day.type)
            }
        }
    }

    private fun changeVisibleET(type: SessionType) {
        when(type){
            SessionType.REST->{
                tvDetailsDistance.isVisible = false
                tvDetailsTime.isVisible = false
                tvDetailsPace.isVisible = false
                etDetailsTime.isVisible = false
                etDetailsPace.isVisible = false
                etDetailsDistance.isVisible = false
            }
            SessionType.WALK,SessionType.TROT,SessionType.REST_OR_TROT_WALK->{
                etDetailsPace.isVisible = false
                tvDetailsPace.isVisible = false
                etDetailsDistance.isVisible = true
                etDetailsTime.isVisible = true
                tvDetailsDistance.isVisible = true
                tvDetailsTime.isVisible = true
            }
            else -> {
                etDetailsPace.isVisible = true
                tvDetailsPace.isVisible = true
                etDetailsDistance.isVisible = true
                etDetailsTime.isVisible = true
                tvDetailsDistance.isVisible = true
                tvDetailsTime.isVisible = true
            }
        }
    }

    private fun changeEnableET(isEditable:Boolean){
        etDetailsTime.isEnabled = isEditable
        etDetailsPace.isEnabled = isEditable
        etDetailsDistance.isEnabled = isEditable
    }

    private fun getWeeksNames(): List<String> {
        val weeksNames = mutableListOf<String>()
        for(week in training.trainingWeeks){
            val name = week.name
            weeksNames.add("Semana $name")
        }
        return weeksNames.toList()
    }

    private fun initComponent() {
        tvTrainingDetailsName = findViewById(R.id.tvTrainingDetailsName)
        tvMonday = findViewById(R.id.tvMonday)
        tvTuesday = findViewById(R.id.tvTuesday)
        tvWednesday = findViewById(R.id.tvWednesday)
        tvThursday = findViewById(R.id.tvThursday)
        tvFriday = findViewById(R.id.tvFriday)
        tvSaturday = findViewById(R.id.tvSaturday)
        tvSunday = findViewById(R.id.tvSunday)
        cardMonday = findViewById(R.id.cardMonday)
        cardTuesday = findViewById(R.id.cardTuesday)
        cardWednesday = findViewById(R.id.cardWednesday)
        cardThursday = findViewById(R.id.cardThursday)
        cardFriday = findViewById(R.id.cardFriday)
        cardSaturday = findViewById(R.id.cardSaturday)
        cardSunday = findViewById(R.id.cardSunday)
        etDetailsDistance = findViewById(R.id.etDetailsDistance)
        etDetailsPace = findViewById(R.id.etDetailsPace)
        etDetailsTime = findViewById(R.id.etDetailsTime)
        spDetailsType = findViewById(R.id.spDetailsType)
        spWeekSelect = findViewById(R.id.spWeekSelect)
        btnEditCreatePlan = findViewById(R.id.btnEditCreatePlan)
        btnMakeActivePlan = findViewById(R.id.btnMakeActivePlan)
        tvDetailsDistance = findViewById(R.id.tvDetailsDistance)
        tvDetailsPace = findViewById(R.id.tvDetailsPace)
        tvDetailsTime = findViewById(R.id.tvDetailsTime)

    }

    private fun savePlan(plansRef: CollectionReference, training:Training){
        val plan =  plansRef.document((training.name))
        plan.set(hashMapOf("title" to training.name, "isActive" to training.isActive)).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Plan guardado correctamente", Toast.LENGTH_SHORT).show()
                var i = 0
                while (i < training.trainingWeeks.size) {
                    val week = training.trainingWeeks[i]
                    val weekRef = plan.collection("weeks").document(week.name)
                    weekRef.set(
                        hashMapOf("title" to week.name)
                    )
                    for (day in week.days) {
                        weekRef.collection("days").document(day.day.toString()).set(
                            hashMapOf(
                                "type" to day.type,
                                "time" to day.time,
                                "distance" to day.distance,
                                "pace" to day.pace
                            )
                        )
                    }
                    i++
                }
                startActivity(Intent(this, MyPlansActivity::class.java).apply {
                    putExtra("email", email)
                })
            } else if (task.isCanceled) {
                Toast.makeText(this, "Error al guardar tu plan", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}