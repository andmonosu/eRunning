package com.andmonosu.erunning.views

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import androidx.core.view.get
import com.andmonosu.erunning.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {
    private var email: String? = null

    private var isRunning:Boolean = false
    private lateinit var chronometer:Chronometer
    private lateinit var btnSession:FloatingActionButton
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
        initListeners()
        return view
    }

    private fun initListeners() {
        btnSession.setOnClickListener{
            isRunning = !isRunning
            if (isRunning){
                chronometer.setBase(SystemClock.elapsedRealtime())
                chronometer.start()
            }else{
                chronometer.stop()
                chronometer.setBase(SystemClock.elapsedRealtime())
            }
        }
    }

    private fun initComponents(view: View) {
        chronometer = view.findViewById(R.id.chronometer)
        btnSession = view.findViewById(R.id.btnSession)
    }

    companion object {

        const val EMAIL_BUNDLE = ""

        @JvmStatic
        fun newInstance(email: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL_BUNDLE, email)
                }
            }
    }
}