package com.retvens.rscoop.OnBoardingScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvens.rscoop.R


class OnBoardingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_screen)

        val viewpager = findViewById<ViewPager>(R.id.viewPager)
        val next = findViewById<TextView>(R.id.Next)
        val skip = findViewById<TextView>(R.id.Skip)

        next.setOnClickListener {
            viewpager?.currentItem = viewpager?.currentItem?.plus(1)!!
            if(viewpager?.currentItem!! >= 2){
                next.visibility = View.GONE
                skip.visibility = View.GONE
            }
            if(viewpager?.currentItem!! <= 1){
                next.visibility = View.VISIBLE
                skip.visibility = View.VISIBLE
            }
        }
        skip.setOnClickListener {
            startActivity(Intent(this@OnBoardingScreen,AdminDashBoard::class.java))
            finish()
        }

        viewpager.adapter = ViewPagerAdapter(supportFragmentManager)
    }
}