package com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile.AddProperty
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile.SelectClient
import com.retvence.rscoop.SharedStorage.SharedPreferenceManagerAdmin
import com.retvens.rscoop.Authentication.LoginActivity
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile.AddClient
import com.retvens.rscoop.MainActivity
import com.retvens.rscoop.R


class ProfileFragment : Fragment() {

    private lateinit var clientLayout:RelativeLayout
    private lateinit var propertyLayout:RelativeLayout

    private lateinit var tncLayout:RelativeLayout
    private lateinit var privacyLayout:RelativeLayout
    private lateinit var helpLayout:RelativeLayout
    private lateinit var aboutLayout:RelativeLayout

    private lateinit var logOut:LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     clientLayout = view.findViewById(R.id.client_layout)
     propertyLayout = view.findViewById(R.id.property_layout)

     tncLayout = view.findViewById(R.id.tnc_layout)
     privacyLayout = view.findViewById(R.id.privacy_layout)
     helpLayout = view.findViewById(R.id.help_layout)
     aboutLayout = view.findViewById(R.id.about_layout)

        logOut = view.findViewById(R.id.logOut)
        logOut.setOnClickListener {
            showCustomDialogBox()
        }

        clientLayout.setOnClickListener {
            startActivity(Intent(context,AddClient::class.java))
        }

        propertyLayout.setOnClickListener {
            startActivity(Intent(context, SelectClient::class.java))
        }


        tncLayout.setOnClickListener {
            Toast.makeText(context,"Working",Toast.LENGTH_LONG)
                .show()
        }

        privacyLayout.setOnClickListener {
            Toast.makeText(context,"Working",Toast.LENGTH_LONG)
                .show()
        }

        helpLayout.setOnClickListener {
            Toast.makeText(context,"Working",Toast.LENGTH_LONG)
                .show()
        }

        aboutLayout.setOnClickListener {
            Toast.makeText(context,"Working",Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun showCustomDialogBox() {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialoge)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)

        btnYes.setOnClickListener {
            context?.let { it1 -> SharedPreferenceManagerAdmin.getInstance(it1).clear() }
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(context,"Logged Out",Toast.LENGTH_SHORT).show()
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}