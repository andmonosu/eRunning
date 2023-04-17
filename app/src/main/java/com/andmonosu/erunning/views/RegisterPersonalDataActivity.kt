package com.andmonosu.erunning.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import com.andmonosu.erunning.MainActivity
import com.andmonosu.erunning.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterPersonalDataActivity : AppCompatActivity() {
    private lateinit var cvImage:CardView
    private lateinit var ivProfilePhoto:ImageView
    private lateinit var tvUPhoto:TextView
    private lateinit var etName:EditText
    private lateinit var etLastName:EditText
    private lateinit var etEmail:EditText
    private lateinit var etPassword:EditText
    private lateinit var btnNext:Button
    private var db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_personal_data)
        initComponents()
        initListeners()
    }
    private fun initComponents() {
        cvImage = findViewById(R.id.cvImageRegister)
        ivProfilePhoto = findViewById(R.id.ivProfilePhotoRegister)
        tvUPhoto = findViewById(R.id.tvUPhotoRegister)
        etName = findViewById(R.id.etNameRegister)
        etLastName = findViewById(R.id.etLastNameRegister)
        etEmail = findViewById(R.id.etEmailRegister)
        etPassword = findViewById(R.id.etPasswordRegister)
        btnNext = findViewById(R.id.btnNextRegister)

    }

    private fun initListeners() {
            btnNext.setOnClickListener{
                val email = etEmail.text
                val password = etPassword.text
                val name = etName.text.toString()
                val lastName = etLastName.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && lastName.isNotEmpty()) {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.toString(), password.toString())
                        .addOnCompleteListener {
                            db.collection("users").document(email.toString()).set(
                                hashMapOf("name" to name, "lastName" to lastName)
                            ).addOnSuccessListener {
                                val intent = Intent(this, RegisterTrainingDataActivity::class.java).apply {
                                    putExtra("email", email.toString())
                                }
                                startActivity(intent)
                            }
                        }
                }else{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Warning")
                    builder.setMessage("No has rellenado alguno de los campos obligatorios (Correo electrónico, contraseña, nombre o apellidos)")
                    builder.setPositiveButton("Aceptar", null)
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
    }
}