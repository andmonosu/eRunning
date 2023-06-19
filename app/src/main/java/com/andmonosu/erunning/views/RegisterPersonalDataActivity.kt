package com.andmonosu.erunning.views
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.andmonosu.erunning.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File

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
    private var storage = FirebaseStorage.getInstance().reference
    private var imagePath: String? = null
    private val PICK_IMAGE_REQUEST_CODE = 1


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
                    val storageReference = storage.child("users/$email.jpg")

                    val imageFile = imagePath?.let { it1 -> File(it1) }
                    if(imageFile != null) {
                        storageReference.putFile(Uri.fromFile(imageFile))
                            .addOnFailureListener {
                                Toast.makeText(this, "Error al subir imagen", Toast.LENGTH_SHORT).show()
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

            cvImage.setOnClickListener {
                openGallery()
                ivProfilePhoto.alpha= 1.0F

            }
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(imageUri!!, projection, null, null, null)
            val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            imagePath = cursor.getString(columnIndex)
            cursor.close()
            val bitmap = BitmapFactory.decodeFile(imagePath)
            ivProfilePhoto.setImageBitmap(bitmap)
        }
    }

}