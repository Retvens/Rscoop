package com.retvence.rscoop.DashBoardIgniter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.retvens.rscoop.R

class DetailTaskActivity : AppCompatActivity() {

    lateinit var edit : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        edit = findViewById(R.id.edit_card)


        val image = findViewById<ImageView>(R.id.hotel_add_task_img)
        val name = findViewById<TextView>(R.id.hotel_name_add_task)
        val facebook = findViewById<TextView>(R.id.fb_post_deatail)
        val instagram = findViewById<TextView>(R.id.instaPost_detail)
        val google = findViewById<TextView>(R.id.google_post_detail)
        val linkedin = findViewById<TextView>(R.id.linkedin_post_detail)
        val tripad = findViewById<TextView>(R.id.tripad_post_detail)
        val twitter = findViewById<TextView>(R.id.twitter_post)
        val pinterest = findViewById<TextView>(R.id.pinterest_post_detail)


        val getid = intent.getStringExtra("id")
        val getimage = intent.getStringExtra("image")
        val getname = intent.getStringExtra("name")
        val getfacebook = intent.getStringExtra("facebook")
        val getinstagram = intent.getStringExtra("instagram")
        val getgoogle = intent.getStringExtra("google")
        val getlinkedin = intent.getStringExtra("linkedin")
        val gettripad = intent.getStringExtra("tripad")
        val gettwitter =intent.getStringExtra("twitter")
        val getpinterest = intent.getStringExtra("pinterest")

        Glide.with(baseContext).load(getimage).into(image)
        name.text = getname
        facebook.text = getfacebook
        instagram.text = getinstagram
        google.text = getgoogle
        linkedin.text = getlinkedin
        tripad.text = gettripad
        twitter.text = gettwitter
        pinterest.text = getpinterest

        edit.setOnClickListener {
            val intent = Intent(baseContext,DetailTaskEditActivity::class.java)
            intent.putExtra("id",getid)
            intent.putExtra("name",getname)
            intent.putExtra("image",getimage)
            intent.putExtra("facebook",getfacebook)
            intent.putExtra("instagram",getinstagram)
            intent.putExtra("google",getgoogle)
            intent.putExtra("linkedin",getlinkedin)
            intent.putExtra("tripad",gettripad)
            intent.putExtra("twitter",gettwitter)
            intent.putExtra("pinterest",getpinterest)
            startActivity(intent)

        }

    }
}