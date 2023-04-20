package com.andmonosu.erunning.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.andmonosu.erunning.AuthActivity
import com.andmonosu.erunning.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File

enum class ProviderType {
    BASIC, GOOGLE
}

class ProfileFragment : Fragment() {
    private var email: String? = null
    private var provider: String? = null
    private var db = FirebaseFirestore.getInstance()

    private lateinit var btnLogout: Button
    private lateinit var btnSave: Button
    private lateinit var tvName: TextView
    private lateinit var etHeight: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAge: EditText
    private lateinit var etGender: EditText
    private lateinit var etActivity: EditText
    private lateinit var etWeight: EditText
    private lateinit var ivProfilePhoto: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString("EMAIL_BUNDLE")
            provider = it.getString("PROVIDER_BUNDLE")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initComponents(view)
        initUI(email?: "")
        setup(email ?: "")
        val prefs =
            activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
                ?.edit()
        if (prefs != null) {
            prefs.putString("email", email)
            prefs.apply()
        }
        return view
    }

    private fun initUI(email: String) {
        db.collection("users").document(email).get().addOnSuccessListener { result ->
            val name = result.get("name") as String
            val lastName = result.get("lastName") as String
            tvName.setText(name + " " +lastName)
            etEmail.setText(email)
            etGender.setText(result.get("gender") as String)
            val age = result.get("age") as Number
            etAge.setText(age.toString())
            val height = result.get("height") as Number
            etHeight.setText(height.toString())
            val weight = result.get("peso") as Number
            etWeight.setText(weight.toString())
            etActivity.setText(result.get("sport activity") as String)
            val storageRef = FirebaseStorage.getInstance().reference.child("users/$email.jpg")
            val localFile = File.createTempFile("tempImage","jpg")
            storageRef.getFile(localFile).addOnSuccessListener{
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                ivProfilePhoto.setImageBitmap(bitmap)
            }
        }
    }

    private fun setup(email: String) {

        btnLogout.setOnClickListener {

            val prefs =
                activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
                    ?.edit()
            if (prefs != null) {
                prefs.clear()
                prefs.apply()
            }
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, AuthActivity::class.java)
            startActivity(intent)
        }

        btnSave.setOnClickListener{
            db.collection("users").document(email).set(
                hashMapOf("peso" to etWeight.text.toString(),"height" to etHeight.text.toString(),"age" to etAge.text.toString(),"gender" to etGender.text.toString(), "activity" to etActivity.toString())
            )
        }
    }

    private fun initComponents(view: View) {
        tvName = view.findViewById(R.id.tvName)
        etEmail = view.findViewById(R.id.etEmail)
        btnLogout = view.findViewById(R.id.btnLogout)
        btnSave = view.findViewById(R.id.btnSave)
        etHeight = view.findViewById(R.id.etHeight)
        etWeight = view.findViewById(R.id.etWeight)
        etAge = view.findViewById(R.id.etAge)
        etGender = view.findViewById(R.id.etGender)
        etActivity = view.findViewById(R.id.etActivity)
        ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto)
    }


    companion object {

        const val EMAIL_BUNDLE = ""
        const val PROVIDER_BUNDLE = ""

        @JvmStatic
        fun newInstance(email: String, password: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL_BUNDLE, email)
                    putString(PROVIDER_BUNDLE, password)
                }
            }
    }
}