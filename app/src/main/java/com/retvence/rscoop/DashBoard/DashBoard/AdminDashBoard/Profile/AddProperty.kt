package com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvens.rscoop.R

class AddProperty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        val backbtn = findViewById<ImageView>(R.id.addproperty_back_btn)
        backbtn.setOnClickListener {
            startActivity(Intent(this, AdminDashBoard::class.java))
        }

    }
}