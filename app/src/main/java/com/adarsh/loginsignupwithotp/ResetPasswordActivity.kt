package com.adarsh.loginsignupwithotp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var btnResetPassword: Button
    lateinit var authorization: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        etEmail = findViewById(R.id.etEmail)
        btnResetPassword = findViewById(R.id.btnReset)

        authorization = FirebaseAuth.getInstance()

        btnResetPassword.setOnClickListener {
            val email = etEmail.text.toString()
            authorization.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    Toast.makeText(
                        this@ResetPasswordActivity,
                        "Please check your Email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this@ResetPasswordActivity, it.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }
}
