package com.andmonosu.erunning

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.viewModels
import com.andmonosu.erunning.ui.viewmodel.UserViewModel
import com.andmonosu.erunning.views.RegisterPersonalDataActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class AuthActivity : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 100

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        initComponent()
        initListeners()
        session()
    }

    override fun onStart() {
        super.onStart()

        authLayout.visibility = View.VISIBLE
    }

    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText

    private lateinit var btnLoginGoogle: Button
    private lateinit var authLayout: LinearLayoutCompat

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val username = prefs.getString("username", null)

        if (username != null) {
            authLayout.visibility = View.INVISIBLE
            showHome(username)
        }
    }

    private fun initComponent() {
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLoginGoogle = findViewById(R.id.btnLoginGoogle)
        authLayout = findViewById(R.id.authLayout)
    }

    private fun initListeners() {
        title = "Autenticación"
        val username = etUsername.text
        val password = etPassword.text
        btnRegister.setOnClickListener {
            showRegister()
        }

        btnLogin.setOnClickListener {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                userViewModel.onCreate(username.toString())
                userViewModel.userModel.observe(this) { user ->
                    Log.i("user", user.toString())
                }
                userViewModel.emailString.observe(this){email->
                    Log.i("email", email)
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(email, password.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                showHome(username.toString())
                            } else {
                                showAlert()
                            }
                        }
                }
                }
        }
        btnLoginGoogle.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent,GOOGLE_SIGN_IN)
        }
    }

    private fun showHome(username: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("username", username)
        }
        startActivity(intent)

    }

    private fun showRegister() {
        val intent = Intent(this, RegisterPersonalDataActivity::class.java)
        startActivity(intent)

    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error durante la autenticación del usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                showHome(account.email ?: "")
                            } else {
                                showAlert()
                            }
                        }
                }

            } catch (e: ApiException) {
                showAlert()
            }
        }

    }
}