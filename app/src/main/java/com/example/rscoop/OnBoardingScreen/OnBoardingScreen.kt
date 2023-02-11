package com.retvens.rscoop.OnBoardingScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.retvens.rscoop.R


class OnBoardingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_screen)



        val viewpager = findViewById<ViewPager>(R.id.viewPager)
        val next = findViewById<TextView>(R.id.Next)



        viewpager.adapter = ViewPagerAdapter(supportFragmentManager)


    }
}