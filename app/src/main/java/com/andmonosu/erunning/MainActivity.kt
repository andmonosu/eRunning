package com.andmonosu.erunning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.andmonosu.erunning.databinding.ActivityMainBinding
import com.andmonosu.erunning.views.HomeFragment
import com.andmonosu.erunning.ui.view.ProfileFragment
import com.andmonosu.erunning.views.SessionsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        val username = extras?.getString("username")
        replaceFragment(HomeFragment().apply {
            this.arguments = bundleOf("USERNAME_BUNDLE" to username)
        })
        binding.navBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navTraining -> replaceFragment(HomeFragment().apply {
                    this.arguments = bundleOf("USERNAME_BUNDLE" to username)
                })
                R.id.navProfile -> replaceFragment(ProfileFragment().apply {
                    this.arguments = bundleOf("USERNAME_BUNDLE" to username)
                })
                R.id.navSessions -> replaceFragment(SessionsFragment().apply {
                    this.arguments = bundleOf("USERNAME_BUNDLE" to username)
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