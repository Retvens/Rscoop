package com.retvence.rscoop.DashBoardIgniter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.retvens.rscoop.R

class DetailTaskActivity : AppCompatActivity() {

    lateinit var edit : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        edit = findViewById(R.id.edit_card)
        edit.setOnClickListener {
            startActivity(Intent(this@DetailTaskActivity,DetailTaskEditActivity::class.java))
        }

    }
}