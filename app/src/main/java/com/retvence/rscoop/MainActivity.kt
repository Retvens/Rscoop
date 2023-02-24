package com.retvens.rscoop

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DashBoardClient.ClientDashboardActivity
import com.retvence.rscoop.DashBoardIgniter.IgniterDashBoard
import com.retvence.rscoop.SharedStorage.SharedPreferenceManagerAdmin
import com.retvens.rscoop.Authentication.LoginActivity
import com.retvens.rscoop.Authentication.LoginPhone

class MainActivity : AppCompatActivity() {
    lateinit var loginBtn: CardView
    lateinit var phoneLogin: CardView
    lateinit var signupBtn: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        signupBtn = findViewById(R.id.signup)
        phoneLogin = findViewById(R.id.phone_cv)
        loginBtn = findViewById(R.id.login_cv)



        loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        phoneLogin.setOnClickListener {
            startActivity(Intent(this, LoginPhone::class.java))
        }


    }

    override fun onStart() {
        super.onStart()
        if (SharedPreferenceManagerAdmin.getInstance(applicationContext).user.message == "Company login successful"){
            val intent = Intent(applicationContext, IgniterDashBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        else if (SharedPreferenceManagerAdmin.getInstance(applicationContext).user.message == "Admin login successful"){
            val intent = Intent(applicationContext,AdminDashBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        else if (SharedPreferenceManagerAdmin.getInstance(this@MainActivity).user.message == "Owner login successful"){
            val intent = Intent(this@MainActivity, ClientDashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}