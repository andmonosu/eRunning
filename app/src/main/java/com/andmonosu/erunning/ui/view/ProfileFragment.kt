package com.andmonosu.erunning.ui.view

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.andmonosu.erunning.AuthActivity
import com.andmonosu.erunning.R
import com.andmonosu.erunning.data.model.User
import com.andmonosu.erunning.ui.viewmodel.UserViewModel
import com.andmonosu.erunning.views.MyPlansActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File

enum class ProviderType {
    BASIC, GOOGLE
}

class ProfileFragment : Fragment() {
    private var username: String? = null
    private var provider: String? = null
    private var db = FirebaseFirestore.getInstance()

    private val userViewModel: UserViewModel by viewModels()

    private lateinit var btnLogout: Button
    private lateinit var btnSave: Button
    private lateinit var btnPlans: Button
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
            username = it.getString("USERNAME_BUNDLE")
            provider = it.getString("PROVIDER_BUNDLE")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initComponents(view)
        initListener()
        initUI(username?: "")
        setup(userViewModel.emailString.value ?: "")
        val prefs =
            activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
                ?.edit()
        if (prefs != null) {
            prefs.putString("username", username)
            prefs.apply()
        }
        return view
    }

    private fun initListener() {
        btnPlans.setOnClickListener {
            val intent = Intent(activity, MyPlansActivity::class.java).apply {
                putExtra("email", userViewModel.emailString.value)
            }
            startActivity(intent)
        }
    }

    private fun initUI(username: String) {
        userViewModel.onCreate(username)
        userViewModel.userModel.observe( viewLifecycleOwner, ) {
            tvName.text = "${it.name} ${it.lastName}"
            etEmail.setText(it.email)
            etGender.setText(it.gender)
            etAge.setText(it.age.toString())
            etHeight.setText(it.height.toString())
            etWeight.setText(it.weight.toString())
            etActivity.setText(it.sportActivity)
            val storageRef = FirebaseStorage.getInstance().reference.child("users/${it.email}.jpg")
            val localFile = File.createTempFile("tempImage", "jpg")
            storageRef.getFile(localFile).addOnSuccessListener {
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
            userViewModel.saveUserModel("andreui", User())
        }
    }

    private fun initComponents(view: View) {
        tvName = view.findViewById(R.id.tvName)
        etEmail = view.findViewById(R.id.etEmail)
        btnLogout = view.findViewById(R.id.btnLogout)
        btnSave = view.findViewById(R.id.btnSave)
        btnPlans = view.findViewById(R.id.btnPlans)
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