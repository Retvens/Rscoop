package com.retvens.rscoop.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.android.material.textfield.TextInputEditText
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DataCollections.UserLoginData
import com.retvence.rscoop.SharedStorage.SharedPreferenceManagerAdmin
import com.retvens.rscoop.OnBoardingScreen.OnBoardingScreen
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        loginBtn.setOnClickListener {
            val email = loginEmail.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            val retroLogin = RetrofitBuilder.retrofitBuilder.userLogin(email,password)
            retroLogin.enqueue(object : Callback<UserLoginData> {
                override fun onResponse(
                    call: Call<UserLoginData>,
                    response: Response<UserLoginData>
                ) {
                    val response = response.body()!!
                    val userType = response.Type_of_acco.toString()
                    SharedPreferenceManagerAdmin.getInstance(this@LoginActivity).saveUser(response)
                    if (userType == "Hotel owner") {
                        val intent = Intent(this@LoginActivity, OnBoardingScreen::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    }else{
                        startActivity(Intent(this@LoginActivity,AdminDashBoard:: class.java))
                        Toast.makeText(this@LoginActivity, "LogIn " + userType, Toast.LENGTH_LONG)
                            .show()
                    }
                }
                override fun onFailure(call: Call<UserLoginData>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
    override fun onStart() {
        super.onStart()
        if (SharedPreferenceManagerAdmin.getInstance(this@LoginActivity).isLoggedIn){
            val intent = Intent(this@LoginActivity,AdminDashBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
         }
    }
}