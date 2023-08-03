package com.adarsh.loginsignupwithotp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)

        setContentView(R.layout.activity_welcome)

        button = findViewById(R.id.btnLogout)
        button.setOnClickListener {

            sharedPreferences.edit().clear().apply()
            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}
