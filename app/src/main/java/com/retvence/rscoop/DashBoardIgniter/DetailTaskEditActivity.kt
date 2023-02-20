package com.retvence.rscoop.DashBoardIgniter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DataCollections.ResponseTask
import com.retvence.rscoop.DataCollections.TaskData
import com.retvence.rscoop.DataCollections.UpdateTaskClass
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTaskEditActivity : AppCompatActivity() {

    private lateinit var facebook:EditText
    private lateinit var instagram:EditText
    private lateinit var google:EditText
    private lateinit var pinterest:EditText
    private lateinit var tripad:EditText
    private lateinit var twitter:EditText
    private lateinit var linkedin:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task_edit)

        val image = findViewById<ImageView>(R.id.hotel_add_task_img)
        val name = findViewById<TextView>(R.id.hotel_name_add_task_Edit)
        facebook = findViewById<EditText>(R.id.fb_post_deatail_Edit)
        instagram = findViewById<EditText>(R.id.instaPost_detail_Edit)
        google = findViewById<EditText>(R.id.google_post_detail_Edit)
        pinterest = findViewById<EditText>(R.id.pinterest_post_detail_Edit)
        tripad = findViewById<EditText>(R.id.tripad_post_detail_Edit)
        twitter = findViewById<EditText>(R.id.twitter_post_Edit)
        linkedin = findViewById<EditText>(R.id.linkedin_post_detail_Edit)
        val button = findViewById<TextView>(R.id.updateSave)


        val getImage = intent.getStringExtra("image")
        val getName = intent.getStringExtra("name")
        val getFacebook = intent.getStringExtra("facebook")
        val getInstagram = intent.getStringExtra("instagram")
        val getGoogle = intent.getStringExtra("google")
        val getPinterest = intent.getStringExtra("pinterest")
        val getTripad = intent.getStringExtra("tripad")
        val getTwitter = intent.getStringExtra("twitter")
        val getLinkedin = intent.getStringExtra("linkedin")


        Glide.with(baseContext).load(getImage).into(image)
        name.text = getName
//        facebook.hint = getFacebook
//        instagram.hint = getInstagram
//        google.hint = getGoogle
//        pinterest.hint = getPinterest
//        tripad.hint = getTripad
//        twitter.hint = getTwitter
//        linkedin.hint = getLinkedin

       button.setOnClickListener {
           updateTask()
       }
    }

    private fun updateTask() {

        val getId = intent.getStringExtra("id")

        val facebook = facebook.text.toString().trim()
        val instagram = instagram.text.toString().trim()
        val google  = google.text.toString().trim()
        val pinterest = pinterest.text.toString().trim()
        val tripad = tripad.text.toString().trim()
        val twitter = twitter.text.toString().trim()
        val linkedin = linkedin.text.toString().trim()

        val send = RetrofitBuilder.retrofitBuilder.updateTask(getId!!, UpdateTaskClass(facebook,linkedin,instagram,twitter,pinterest,tripad,google))

        send.enqueue(object : Callback<ResponseTask?> {
            override fun onResponse(call: Call<ResponseTask?>, response: Response<ResponseTask?>) {
                if (response.isSuccessful){
                    val response = response.body()
                    Toast.makeText(applicationContext,response!!.message,Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext,response.code().toString(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseTask?>, t: Throwable) {
                Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
            }
        })

    }
}