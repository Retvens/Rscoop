package com.example.rscoop.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.rscoop.R

class LoginPhone : AppCompatActivity() {

    lateinit var send: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_phone)
        send = findViewById(R.id.send_otp)
        send.setOnClickListener {
            startActivity(Intent(this,PhoneVerification:: class.java))
        }

    }
}