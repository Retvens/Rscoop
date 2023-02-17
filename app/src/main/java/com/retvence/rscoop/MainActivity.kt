package com.retvens.rscoop

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.retvens.rscoop.Authentication.LoginActivity
import com.retvens.rscoop.Authentication.LoginPhone
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard


class MainActivity : AppCompatActivity() {
    lateinit var loginBtn : CardView
    lateinit var phoneLogin : CardView
    lateinit var signupBtn : TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        signupBtn = findViewById(R.id.signup)
        phoneLogin = findViewById(R.id.phone_cv)
        loginBtn = findViewById(R.id.login_cv)



        loginBtn.setOnClickListener {
            startActivity( Intent(this, LoginActivity::class.java))
        }

        phoneLogin.setOnClickListener{
            startActivity(Intent(this, LoginPhone::class.java))
        }


    }

}