package com.andmonosu.erunning.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.andmonosu.erunning.AuthActivity
import com.andmonosu.erunning.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

enum class ProviderType {
    BASIC, GOOGLE
}

class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var email: String? = null
    private var provider: String? = null
    private var db = FirebaseFirestore.getInstance()

    private lateinit var tvProvider: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button
    private lateinit var btnGet: Button
    private lateinit var etHeight: EditText
    private lateinit var etWeight: EditText


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
        setup(email ?: "", provider ?: "")

        //Guardar datos

        val prefs =
            activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
                ?.edit()
        if (prefs != null) {
            prefs.putString("email", email)
            prefs.putString("provider", provider)
            prefs.apply()
        }
        return view
    }

    private fun setup(email: String, provider: String) {
        tvProvider.text = provider
        tvEmail.text = email

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
                hashMapOf("provider" to provider, "peso" to etWeight.text.toString(),"height" to etHeight.text.toString())
            )
        }
        btnDelete.setOnClickListener{

        }
        btnGet.setOnClickListener{
            db.collection("users").document(email).collection("entrenamientos").document("1").get().addOnSuccessListener {
                etHeight.setText(it.get("duracion") as String?)
            }
        }
    }

    private fun initComponents(view: View) {
        tvProvider = view.findViewById(R.id.tvProvider)
        tvEmail = view.findViewById(R.id.tvEmail)
        btnLogout = view.findViewById(R.id.btnLogout)
        btnDelete = view.findViewById(R.id.btnDelete)
        btnSave = view.findViewById(R.id.btnSave)
        btnGet = view.findViewById(R.id.btnGet)
        etHeight = view.findViewById(R.id.etHeight)
        etWeight = view.findViewById(R.id.etWeight)
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