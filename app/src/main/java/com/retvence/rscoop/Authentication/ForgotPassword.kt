package com.retvens.rscoop.Authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.google.android.material.textfield.TextInputEditText
import com.retvens.rscoop.R

class ForgotPassword : AppCompatActivity() {

    lateinit var sendBtn : CardView
    lateinit var passwordEdit: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        passwordEdit = findViewById(R.id.f_mail_e_text)
        sendBtn = findViewById(R.id.send_f_password)

    }
}