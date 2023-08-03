package com.adarsh.loginsignupwithotp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adarsh.loginsignupwithotp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var authorization: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isLoggedIn) {
            val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        authorization = FirebaseAuth.getInstance()

        binding.txtSignUp.setOnClickListener {
            val intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.txtForgotPassword.setOnClickListener {
            val intent = Intent(this@MainActivity, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text.toString().isEmpty()) {
                binding.etEmail.error = "Enter Email"
            } else if (binding.etPassword.text.toString().isEmpty()) {
                binding.etPassword.error = "Enter Password"
            } else {
                authorization.signInWithEmailAndPassword(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {

                        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

                        val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Incorrect Credentials! \n Please Enter correct email & password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
