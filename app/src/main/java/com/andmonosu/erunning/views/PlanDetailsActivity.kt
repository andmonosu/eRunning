package com.andmonosu.erunning.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.andmonosu.erunning.R
import com.andmonosu.erunning.models.Training
import com.andmonosu.erunning.models.TrainingWeek
import java.time.DayOfWeek

class PlanDetailsActivity : AppCompatActivity() {

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
    private lateinit var etDetailsType:EditText
    private lateinit var etDetailsTime:EditText
    private lateinit var etDetailsPace:EditText
    private lateinit var etDetailsDistance:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_details)
        training = intent.getParcelableExtra("training")!!
        initComponent()
        initUI()
        initListeners()
    }

    private fun initListeners() {
        cardMonday.setOnClickListener {
            changeSelectedDay(cardMonday,tvMonday)
            showDayInformation(DayOfWeek.MONDAY)
        }
        cardTuesday.setOnClickListener {
            changeSelectedDay(cardTuesday,tvTuesday)
            showDayInformation(DayOfWeek.TUESDAY)
        }
        cardWednesday.setOnClickListener {
            changeSelectedDay(cardWednesday,tvWednesday)
            showDayInformation(DayOfWeek.WEDNESDAY)
        }
        cardThursday.setOnClickListener {
            changeSelectedDay(cardThursday,tvThursday)
            showDayInformation(DayOfWeek.THURSDAY)
        }
        cardFriday.setOnClickListener {
            changeSelectedDay(cardFriday,tvFriday)
            showDayInformation(DayOfWeek.FRIDAY)
        }
        cardSaturday.setOnClickListener {
            changeSelectedDay(cardSaturday,tvSaturday)
            showDayInformation(DayOfWeek.SATURDAY)

        }
        cardSunday.setOnClickListener {
            changeSelectedDay(cardSunday,tvSunday)
            showDayInformation(DayOfWeek.SUNDAY)
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
                etDetailsType.setText(day.type.toString())
                etDetailsTime.setText(day.time.toString())
            }
        }
    }

    private fun initUI() {
        tvTrainingDetailsName.text = training.name
        trainingWeekSelected = training.trainingWeeks[0]
        cardMonday.setCardBackgroundColor(ContextCompat.getColor(this,R.color.background_button))
        tvMonday.setTextColor(ContextCompat.getColor(this,R.color.white))
        tvDaySelected = tvMonday
        cardDaySelected = cardMonday
        showDayInformation(DayOfWeek.MONDAY)
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
        etDetailsType = findViewById(R.id.etDetailsType)
    }
}