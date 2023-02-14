package com.retvens.rscoop.OnBoardingScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvens.rscoop.R


class OnBoardingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_screen)


        val start = findViewById<CardView>(R.id.start_btn)
        start.setOnClickListener {
            startActivity(Intent(this@OnBoardingScreen,AdminDashBoard::class.java))
            finish()
        }

        val viewpager = findViewById<ViewPager>(R.id.viewPager)
        val next = findViewById<TextView>(R.id.Next)



        viewpager.adapter = ViewPagerAdapter(supportFragmentManager)


    }
}