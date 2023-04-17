package com.andmonosu.erunning.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.andmonosu.erunning.MainActivity
import com.andmonosu.erunning.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.text.DecimalFormat

class RegisterTrainingDataActivity : AppCompatActivity() {

    private var isMaleSelected:Boolean = true
    private var isFemaleSelected:Boolean = false
    private var currentWeight:Int = 60
    private var currentAge:Int = 25
    private var currentHeight:Int = 120
    private var currentSportActivity:String = "Nada"
    private var db = FirebaseFirestore.getInstance()

    private lateinit var viewMale:CardView
    private lateinit var viewFemale:CardView
    private lateinit var tvHeight:TextView
    private lateinit var rsHeight:RangeSlider
    private lateinit var tvSportActivity:TextView
    private lateinit var rsSportActivity:RangeSlider
    private lateinit var btnSubtractWeight: FloatingActionButton
    private lateinit var btnAddWeight: FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var btnSubtractAge: FloatingActionButton
    private lateinit var btnAddAge: FloatingActionButton
    private lateinit var tvAge: TextView
    private lateinit var btnCalculate:Button
    private lateinit var tvMale:TextView
    private lateinit var tvFemale:TextView
    private lateinit var ivMale:ImageView
    private lateinit var ivFemale:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_training_data)

        initComponents()
        initListeners()
        initUI()
    }

    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        tvSportActivity = findViewById(R.id.tvSportActivity)
        rsSportActivity = findViewById(R.id.rsSportActivity)
        btnSubtractWeight = findViewById(R.id.btnSubtractWeight)
        btnAddWeight = findViewById(R.id.btnAddWeight)
        tvWeight = findViewById(R.id.tvWeight)
        btnSubtractAge = findViewById(R.id.btnSubtractAge)
        btnAddAge = findViewById(R.id.btnAddAge)
        tvAge = findViewById(R.id.tvAge)
        btnCalculate = findViewById(R.id.btnCalculate)
        tvMale = findViewById(R.id.tvMale)
        tvFemale = findViewById(R.id.tvFemale)
        ivMale = findViewById(R.id.ivMale)
        ivFemale = findViewById(R.id.ivFemale)
    }

    private fun initListeners() {
        viewMale.setOnClickListener{
            changeGender("male")
            setGenderColor()
        }
        viewFemale.setOnClickListener{
            changeGender("female")
            setGenderColor()
        }
        rsHeight.addOnChangeListener {_,value,_ ->
            val df = DecimalFormat("#.##")
            currentHeight = df.format(value).toInt()
            tvHeight.text = "$currentHeight cm"
        }

        rsSportActivity.addOnChangeListener {_,value,_ ->
            when(value){
                1.0f-> {
                    currentSportActivity = "Nada"
                    setSportActivity()
                }
                2.0f-> {
                    currentSportActivity = "Poco"
                    setSportActivity()
                }
                3.0f-> {
                    currentSportActivity = "Algo"
                    setSportActivity()
                }
                4.0f-> {
                    currentSportActivity = "Mucho"
                    setSportActivity()
                }
            }
        }

        btnAddWeight.setOnClickListener{
            currentWeight += 1
            setWeight()
        }
        btnSubtractWeight.setOnClickListener{
            currentWeight -= 1
            setWeight()
        }

        btnAddAge.setOnClickListener{
            currentAge += 1
            setAge()
        }
        btnSubtractAge.setOnClickListener{
            currentAge -= 1
            setAge()
        }

        btnCalculate.setOnClickListener{
            navigateToHome()
        }
    }

    private fun getGender(): String {
        if(isMaleSelected){
            return "Male"
        }else if (isFemaleSelected){
            return "Female"
        }
        return ""
    }

    private fun navigateToHome(){
        val extras = intent.extras
        val email = extras?.getString("email")
        val navigate = Intent(this, MainActivity::class.java)
        val gender = getGender()
        db.collection("users").document(email.toString()).set(
            hashMapOf("peso" to currentWeight,"height" to currentHeight,"gender" to gender,"age" to currentAge, "sport activity" to currentSportActivity), SetOptions.merge()
        ).addOnSuccessListener {
            startActivity(navigate)
        }
    }

    private fun setWeight() {
        tvWeight.text = currentWeight.toString()
    }

    private fun setAge() {
        tvAge.text = currentAge.toString()
    }

    private fun setSportActivity() {
        tvSportActivity.text = currentSportActivity
    }

    private fun changeGender(genderClicked:String){
        if((genderClicked==="male"&&!isMaleSelected)||(genderClicked==="female"&&!isFemaleSelected)){
            isMaleSelected = !isMaleSelected
            isFemaleSelected = !isFemaleSelected
        }
    }

    private fun setGenderColor(){
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
        tvMale.setTextColor(getTextImageColor(isMaleSelected))
        tvFemale.setTextColor(getTextImageColor(isFemaleSelected))
        ivMale.setColorFilter(getTextImageColor(isMaleSelected))
        ivFemale.setColorFilter(getTextImageColor(isFemaleSelected))
    }

    private fun getBackgroundColor(isSelectedComponent:Boolean):Int{
        val colorReference = if(isSelectedComponent){
            R.color.background_component_selected
        }else{
            R.color.background_component
        }
        return ContextCompat.getColor(this,colorReference)
    }

    private fun getTextImageColor(isSelectedComponent:Boolean):Int{
        val colorReference = if(isSelectedComponent){
            R.color.title_text_selected
        }else{
            R.color.title_text
        }
        return ContextCompat.getColor(this,colorReference)
    }

    private fun initUI() {
        setGenderColor()
        setWeight()
        setAge()
        setSportActivity()
    }
}