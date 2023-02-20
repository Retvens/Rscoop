package com.retvence.rscoop.DashBoardIgniter.TaskFragment

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
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.retvence.rscoop.DashBoardIgniter.AddNewTaskActivity
import com.retvence.rscoop.DashBoardIgniter.DetailTaskActivity
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvence.rscoop.DataCollections.TaskData
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.RecentRecycler
import com.retvens.rscoop.R

class RecentAdapter(val context: Context, var userList:List<GetTaskData>):RecyclerView.Adapter<RecentAdapter.holderclass>() {
    class holderclass(itemview: View):RecyclerView.ViewHolder(itemview) {
        var name = itemview.findViewById<TextView>(R.id.Company_Name)
        var image = itemview.findViewById<ImageView>(R.id.owner_Image)

        var facebook = itemview.findViewById<TextView>(R.id.facebooktask)
        var instagram = itemview.findViewById<TextView>(R.id.instatask)
        var linkdin = itemview.findViewById<TextView>(R.id.linkdin)
        var twitter = itemview.findViewById<TextView>(R.id.twitter)
        var pinterest = itemview.findViewById<TextView>(R.id.pinterest)
        var gmb = itemview.findViewById<TextView>(R.id.gg)
        var google = itemview.findViewById<TextView>(R.id.google)

        var fbIcon = itemView.findViewById<ImageView>(R.id.fb_icon)
        var linkIcon = itemView.findViewById<ImageView>(R.id.linkdin_icon)
        var instaIcon = itemView.findViewById<ImageView>(R.id.ig_icon)
        var twitterIcon = itemView.findViewById<ImageView>(R.id.twitter_icon)
        var pinterestIcon = itemView.findViewById<ImageView>(R.id.pinterest_icon)
        var tripadViserIcon = itemView.findViewById<ImageView>(R.id.ullu)
        var googleIcon = itemView.findViewById<ImageView>(R.id.google_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holderclass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recentcard,parent,false)
        return holderclass(view)
    }

    override fun onBindViewHolder(holder: holderclass, position: Int) {

        val item = userList[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(context,DetailTaskActivity::class.java)
            intent.putExtra("id",item._id)
            intent.putExtra("name",item.hotel_name)
            intent.putExtra("image",item.owner_pic)
            intent.putExtra("facebook",item.facebook)
            intent.putExtra("instagram",item.instagram)
            intent.putExtra("linkedin",item.Linkedin)
            intent.putExtra("twitter",item.twitter)
            intent.putExtra("google",item.Google_reviews)
            intent.putExtra("tripad",item.GMB)
            intent.putExtra("pinterest",item.Pinterest)
            context.startActivity(intent)

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

    override fun getItemCount(): Int {
        return userList.size
    }
}