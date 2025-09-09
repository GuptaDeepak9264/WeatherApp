package com.example.weatherapp.ui

import com.example.weatherapp.LoginActivity


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerBtn: Button
    private lateinit var loginRedirectBtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        emailInput = findViewById(R.id.etEmail)
        passwordInput = findViewById(R.id.etPassword)
        registerBtn = findViewById(R.id.btnRegister)
        loginRedirectBtn = findViewById(R.id.tvGoToLogin)

        // REGISTER logic
        registerBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Register Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Redirect to Login Page
        loginRedirectBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
