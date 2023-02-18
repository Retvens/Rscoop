package com.retvens.rscoop.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.bumptech.glide.manager.RequestTracker
import com.google.android.material.textfield.TextInputEditText
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DashBoardIgniter.IgniterDashBoard
import com.retvence.rscoop.DataCollections.LoginResponse
import com.retvence.rscoop.DataCollections.UserLoginData
import com.retvens.rscoop.OnBoardingScreen.OnBoardingScreen
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var signupBtn: TextView
    lateinit var forgetPassBtn: TextView
    lateinit var loginBtn: CardView

    lateinit var loginPassword: EditText
    lateinit var loginEmail: EditText

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

            loginUser()

        }
    }

    private fun loginUser() {
        val email = loginEmail.text.toString()
        val password = loginPassword.text.toString()
        val login = RetrofitBuilder.retrofitBuilder.userLogin(UserLoginData(email,password,""))
        login.enqueue(object : Callback<UserLoginData?> {
            override fun onResponse(
                call: Call<UserLoginData?>,
                response: Response<UserLoginData?>
            ) {
               if (response.isSuccessful){
                    val response = response.body()!!
                    if (response.message.toString() == "Admin login successful"){
                        startActivity(Intent(this@LoginActivity,AdminDashBoard::class.java))
                    }else if (response.message.toString() == "Company login successful"){
                        Toast.makeText(applicationContext,response.message,Toast.LENGTH_LONG).show()
                        startActivity(Intent(applicationContext,IgniterDashBoard::class.java))
                    } else if (response.message.toString() == "Owner login successful"){
                        Toast.makeText(applicationContext,response.message,Toast.LENGTH_LONG).show()
                    }
                   else{
                        Toast.makeText(applicationContext,response.message,Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(applicationContext,response.body()!!.message,Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<UserLoginData?>, t: Throwable) {
                Toast.makeText(applicationContext,t.localizedMessage,Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
       /* if (SharedPreferenceManagerAdmin.getInstance(this@LoginActivity).isLoggedIn){
            val intent = Intent(this@LoginActivity,AdminDashBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
         }*/
    }
}