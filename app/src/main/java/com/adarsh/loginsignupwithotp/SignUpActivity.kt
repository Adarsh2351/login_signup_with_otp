package com.adarsh.loginsignupwithotp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adarsh.loginsignupwithotp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            if (binding.etEmail.text.toString().isEmpty()) {
                binding.etEmail.error = "Enter Email"
            } else if (binding.etPassword.text.toString().isEmpty()) {
                binding.etPassword.error = "Enter Password"
            } else if (binding.etConfirmPassword.text.toString().isEmpty()) {
                binding.etConfirmPassword.error = "Enter Password Again"
            } else if (!(binding.etPassword.text.toString()
                    .equals(binding.etConfirmPassword.text.toString()))
            ) {
                binding.etConfirmPassword.error = "Password must be same"
            } else {
                val intent = Intent(this@SignUpActivity, OtpActivity::class.java)
                intent.putExtra("email", binding.etEmail.text.toString())
                intent.putExtra("password", binding.etPassword.text.toString())
                startActivity(intent)
            }
        }
        binding.txtAlreadyAccount.setOnClickListener {
            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
