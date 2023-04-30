package com.andmonosu.erunning.views

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentContainerView
import com.andmonosu.erunning.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.round

class HomeFragment : Fragment() {
    private var email: String? = null
    private var db = FirebaseFirestore.getInstance()
    private var isRunning:Boolean = false
    private lateinit var chronometer:Chronometer
    private lateinit var btnSession:FloatingActionButton
    private lateinit var mapFragment: FragmentContainerView
    private val mapsFragment:MapsFragment = MapsFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString("EMAIL_BUNDLE")
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_home, container, false)
        initComponents(view)
        initUI()
        initListeners()
        return view
    }

    private fun initUI() {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mapFragment, mapsFragment)
        fragmentTransaction.commit()
    }

    private fun initListeners() {
        btnSession.setOnClickListener{
            isRunning = !isRunning
            if (isRunning){
                chronometer.setBase(SystemClock.elapsedRealtime())
                mapsFragment.startTracking()
                mapsFragment.map.clear()
                chronometer.start()
            }else{
                val timeElapsed = SystemClock.elapsedRealtime() - chronometer.base
                var timeElapsedInMinutes = timeElapsed / 60000.0
                timeElapsedInMinutes = round(timeElapsedInMinutes*100)/100.0
                chronometer.stop()
                mapsFragment.stopTracking()
                val currentDate = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val distance = mapsFragment.ui.value?.formattedDistance
                val pace = mapsFragment.ui.value?.formattedPace
                val dateNow = currentDate.format(formatter)
                db.collection("users").document(email?:"").collection("sessions").document(dateNow.toString()).set(
                    hashMapOf("distancia" to distance,"ritmo" to pace,"tiempo" to "$timeElapsedInMinutes min")
                ).addOnSuccessListener {
                    Toast.makeText(requireActivity(), "Sesión guardada correctamente", Toast.LENGTH_SHORT).show()
                }
                chronometer.setBase(SystemClock.elapsedRealtime())
            }
        }
    }

    private fun initComponents(view: View) {
        chronometer = view.findViewById(R.id.chronometer)
        btnSession = view.findViewById(R.id.btnSession)
        mapFragment = view.findViewById(R.id.mapFragment)
    }

    companion object {

        const val EMAIL_BUNDLE = ""

        @JvmStatic
        fun newInstance(email: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL_BUNDLE, email)
                }
            }
    }
}