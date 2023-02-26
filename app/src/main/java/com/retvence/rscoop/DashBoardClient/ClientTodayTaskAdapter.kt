package com.retvence.rscoop.DashBoardClient

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvence.rscoop.DataCollections.ResponseTask
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormatSymbols
import java.util.*


class ClientTodayTaskAdapter(val context: Context, var userList:List<GetTaskData>) : RecyclerView.Adapter<ClientTodayTaskAdapter.ClientTodayTaskViewHolder>() {

    var adapterPosition = -1
    var favourite = false

    class ClientTodayTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var name = itemView.findViewById<TextView>(R.id.Company_Name)
        var image = itemView.findViewById<ImageView>(R.id.owner_Image)
        var favorite = itemView.findViewById<ImageView>(R.id.favorite_icon)
        var status = itemView.findViewById<ImageView>(R.id.status_Image)
        var date = itemView.findViewById<TextView>(R.id.hotel_date)
        var facebook = itemView.findViewById<TextView>(R.id.facebooktask)
        var instagram = itemView.findViewById<TextView>(R.id.instatask)
        var linkdin = itemView.findViewById<TextView>(R.id.linkdin)
        var twitter = itemView.findViewById<TextView>(R.id.twitter)
        var pinterest = itemView.findViewById<TextView>(R.id.pinterest)
        var gmb = itemView.findViewById<TextView>(R.id.gg)
        var google = itemView.findViewById<TextView>(R.id.google)

        var fbIcon = itemView.findViewById<ImageView>(R.id.fb_icon)
        var linkIcon = itemView.findViewById<ImageView>(R.id.linkdin_icon)
        var instaIcon = itemView.findViewById<ImageView>(R.id.ig_icon)
        var twitterIcon = itemView.findViewById<ImageView>(R.id.twitter_icon)
        var pinterestIcon = itemView.findViewById<ImageView>(R.id.pinterest_icon)
        var tripadViserIcon = itemView.findViewById<ImageView>(R.id.ullu)
        var googleIcon = itemView.findViewById<ImageView>(R.id.google_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientTodayTaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recentcard,parent,false)
        return ClientTodayTaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ClientTodayTaskViewHolder, position: Int) {

        if(adapterPosition == position){
            holder.favorite.setColorFilter(context.getResources().getColor(R.color.sky_blue))
        }else{
            holder.favorite.setColorFilter(context.getResources().getColor(R.color.light_grey))
        }
        if (userList[position].Status == "Done"){
            holder.status.setImageResource(R.drawable.png_check)
        }else{
            holder.status.setImageResource(R.drawable.png_timer)
        }

        if (userList[position].favourite == true) {
            holder.favorite.setColorFilter(context.getResources().getColor(R.color.sky_blue))
        }

        holder.favorite.setOnClickListener {
            adapterPosition = position
            notifyDataSetChanged()
            favourite = true
            val id = userList[position]._id
            setFavourite(id)
        }
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val monthName = DateFormatSymbols().months[month]

        val currentDate = "$day"+" "+"$monthName"+" "+"$year"

        if (userList[position].Date.toString() == currentDate){
            holder.itemView.visibility = View.VISIBLE
        }else{
            holder.itemView.visibility = View.GONE
        }


            holder.facebook.text = userList[position].facebook
            holder.instagram.text = userList[position].instagram
            holder.linkdin.text = userList[position].Linkedin
            holder.twitter.text = userList[position].twitter
            holder.pinterest.text = userList[position].Pinterest
            holder.gmb.text = userList[position].GMB
            holder.google.text = userList[position].Google_reviews
            Glide.with(context).load(userList[position].owner_pic).into(holder.image)
            holder.name.text = userList[position].hotel_name
            holder.date.text = userList[position].Date

            holder.favorite.visibility = View.VISIBLE

            holder.itemView.setOnClickListener {
                val intent = Intent(context, ClientDetailTaskActivity::class.java)
                intent.putExtra("nameHotel", userList[position].hotel_name)
                intent.putExtra("imageCover", userList[position].owner_pic)
                intent.putExtra("facebook", userList[position].facebook)
                intent.putExtra("instagram", userList[position].instagram)
                intent.putExtra("linkedin", userList[position].Linkedin)
                intent.putExtra("twitter", userList[position].twitter)
                intent.putExtra("google", userList[position].Google_reviews)
                intent.putExtra("tripad", userList[position].GMB)
                intent.putExtra("pinterest", userList[position].Pinterest)
                intent.putExtra("status",userList[position].Status)
                context.startActivity(intent)
            }

        holder.fbIcon.setOnClickListener{
            val uriForFB : Uri = Uri.parse("https://facebook.com")
            val openFB = Intent(Intent.ACTION_VIEW,uriForFB)
            context.startActivity(openFB)
        }
        holder.instaIcon.setOnClickListener {
            val uriForinsta: Uri = Uri.parse("http://instagram.com")
            val forinsta = Intent(Intent.ACTION_VIEW, uriForinsta)
            context.startActivity(forinsta)
        }
        holder.linkIcon.setOnClickListener {
            val uriForLink : Uri = Uri.parse("https://linkedin.com")
            context.startActivity(Intent(Intent.ACTION_VIEW,uriForLink))
        }
        holder.twitterIcon.setOnClickListener{
            val uriFortwitter : Uri = Uri.parse("https://twitter.com")
            val openTwitter = Intent(Intent.ACTION_VIEW,uriFortwitter)
            context.startActivity(openTwitter)
        }
        holder.pinterestIcon.setOnClickListener{
            val uriForPinterest : Uri = Uri.parse("https://pinterest.com")
            val openPint = Intent(Intent.ACTION_VIEW,uriForPinterest)
            context.startActivity(openPint)
        }
        holder.tripadViserIcon.setOnClickListener{
            val uriForTripod : Uri = Uri.parse("https://tripadvisor.com")
            val openTripadVisor = Intent(Intent.ACTION_VIEW,uriForTripod)
            context.startActivity(openTripadVisor)
        }
        holder.googleIcon.setOnClickListener {
            val uriForGogle : Uri = Uri.parse("https://google.com")
            val openGoogle = Intent(Intent.ACTION_VIEW,uriForGogle)
            context.startActivity(openGoogle)
        }

    }

    private fun setFavourite(id: String) {
        val fav = RetrofitBuilder.retrofitBuilder.setFav(id, FavouriteDataClass(favourite))
        fav.enqueue(object : Callback<ResponseTask?> {
            override fun onResponse(call: Call<ResponseTask?>, response: Response<ResponseTask?>) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    Toast.makeText(context,response.message.toString(), Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,response.code().toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseTask?>, t: Throwable) {
                Toast.makeText(context,t.localizedMessage!!.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}