package com.adarsh.loginsignupwithotp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.adarsh.loginsignupwithotp.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import papaya.`in`.sendmail.SendMail
import kotlin.random.Random
import kotlin.random.nextInt

class OtpActivity : AppCompatActivity() {

    lateinit var binding: ActivityOtpBinding
    lateinit var authorization: FirebaseAuth
    var email: String = ""
    var password: String = ""
    var random: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = intent.getStringExtra("email").toString()
        password = intent.getStringExtra("password").toString()
        authorization = FirebaseAuth.getInstance()

        random()

        binding.showEmail.setText(email.toString())

        binding.txtResendOtp.setOnClickListener {
            random()
        }

        binding.otp1.doOnTextChanged { text, start, before, count ->
            if (!binding.otp1.text.toString().isEmpty()) {
                binding.otp2.requestFocus()
            }
            if (!binding.otp2.text.toString().isEmpty()) {
                binding.otp2.requestFocus()
            }
        }
        binding.otp2.doOnTextChanged { text, start, before, count ->
            if (!binding.otp2.text.toString().isEmpty()) {
                binding.otp3.requestFocus()
            } else {
                binding.otp1.requestFocus()
            }
        }
        binding.otp3.doOnTextChanged { text, start, before, count ->
            if (!binding.otp3.text.toString().isEmpty()) {
                binding.otp4.requestFocus()
            } else {
                binding.otp2.requestFocus()
            }
        }
        binding.otp4.doOnTextChanged { text, start, before, count ->
            if (!binding.otp4.text.toString().isEmpty()) {
                binding.otp5.requestFocus()
            } else {
                binding.otp3.requestFocus()
            }
        }
        binding.otp5.doOnTextChanged { text, start, before, count ->
            if (!binding.otp5.text.toString().isEmpty()) {
                binding.otp6.requestFocus()
            } else {
                binding.otp4.requestFocus()
            }
        }
        binding.otp6.doOnTextChanged { text, start, before, count ->
            if (binding.otp6.text.toString().isEmpty()) {
                binding.otp5.requestFocus()
            }
            binding.btnDone.setOnClickListener {
                var otp1 = binding.otp1.text.toString()
                var otp2 = binding.otp2.text.toString()
                var otp3 = binding.otp3.text.toString()
                var otp4 = binding.otp4.text.toString()
                var otp5 = binding.otp5.text.toString()
                var otp6 = binding.otp6.text.toString()

                var otp = "$otp1$otp2$otp3$otp4$otp5$otp6"

                if (binding.otp1.text.toString().isEmpty() ||
                    binding.otp2.text.toString().isEmpty() ||
                    binding.otp3.text.toString().isEmpty() ||
                    binding.otp4.text.toString().isEmpty() ||
                    binding.otp5.text.toString().isEmpty() ||
                    binding.otp6.text.toString().isEmpty()
                ) {
                    Toast.makeText(this@OtpActivity, "Enter Otp", Toast.LENGTH_SHORT).show()
                } else if (!otp.equals(random.toString())) {
                    Toast.makeText(this@OtpActivity, "Wrong OTP", Toast.LENGTH_SHORT).show()
                } else {
                    authorization.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this@OtpActivity, WelcomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this@OtpActivity,
                                    it.exception?.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }

    fun random() {
        random = Random.nextInt(100000..999999)
        val mail = SendMail(
            "adarshkumarbarnwal2751@gmail.com", "gtupboukhksamwnf", email, "Login Signup app's OTP",
            "Your OTP is -> $random"
        )
        mail.execute()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
