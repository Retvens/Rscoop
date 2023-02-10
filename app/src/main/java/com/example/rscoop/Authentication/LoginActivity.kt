package com.example.rscoop.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.rscoop.R
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    lateinit var signupBtn: TextView
    lateinit var forgetPassBtn: TextView
    lateinit var loginBtn: CardView

    lateinit var loginPassword: TextInputEditText
    lateinit var loginEmail: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPassword = findViewById(R.id.login_password)
        loginEmail = findViewById(R.id.login_email)

        signupBtn = findViewById(R.id.signup_btn)
        forgetPassBtn = findViewById(R.id.forget_password_btn)
        loginBtn = findViewById(R.id.login_btn)

        forgetPassBtn.setOnClickListener {
            startActivity(Intent(this,ForgotPassword::class.java))
        }

    }
}