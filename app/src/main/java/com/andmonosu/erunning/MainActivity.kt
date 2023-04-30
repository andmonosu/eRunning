package com.andmonosu.erunning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.andmonosu.erunning.R
import com.andmonosu.erunning.databinding.ActivityMainBinding
import com.andmonosu.erunning.views.HomeFragment
import com.andmonosu.erunning.views.ProfileFragment
import com.andmonosu.erunning.views.ProfileFragment.Companion.EMAIL_BUNDLE
import com.andmonosu.erunning.views.ProfileFragment.Companion.PROVIDER_BUNDLE
import com.andmonosu.erunning.views.SessionsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        val email = extras?.getString("email")
        replaceFragment(HomeFragment().apply {
            this.arguments = bundleOf("EMAIL_BUNDLE" to email)
        })
        binding.navBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navTraining -> replaceFragment(HomeFragment().apply {
                    this.arguments = bundleOf("EMAIL_BUNDLE" to email)
                })
                R.id.navProfile -> replaceFragment(ProfileFragment().apply {
                    this.arguments = bundleOf("EMAIL_BUNDLE" to email)
                })
                R.id.navSessions -> replaceFragment(SessionsFragment().apply {
                    this.arguments = bundleOf("EMAIL_BUNDLE" to email)
                })
                else -> {

                }
            }
            true
        }


    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, fragment)
        fragmentTransaction.commit()
    }
}