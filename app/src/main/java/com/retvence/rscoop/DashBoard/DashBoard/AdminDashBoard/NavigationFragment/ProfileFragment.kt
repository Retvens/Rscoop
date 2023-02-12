package com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile.AddClient
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile.AddProperty
import com.retvens.rscoop.R


class ProfileFragment : Fragment() {

    private lateinit var clientLayout:RelativeLayout
    private lateinit var propertyLayout:RelativeLayout
    private lateinit var countryLayout:RelativeLayout
    private lateinit var cityLayout:RelativeLayout

    private lateinit var tncLayout:RelativeLayout
    private lateinit var privacyLayout:RelativeLayout
    private lateinit var helpLayout:RelativeLayout
    private lateinit var aboutLayout:RelativeLayout

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
     countryLayout = view.findViewById(R.id.country_layout)
     cityLayout = view.findViewById(R.id.city_layout)

     tncLayout = view.findViewById(R.id.tnc_layout)
     privacyLayout = view.findViewById(R.id.privacy_layout)
     helpLayout = view.findViewById(R.id.help_layout)
     aboutLayout = view.findViewById(R.id.about_layout)

        clientLayout.setOnClickListener {
            startActivity(Intent(context,AddClient::class.java))
        }

        propertyLayout.setOnClickListener {
            startActivity(Intent(context, AddProperty::class.java))
        }

        countryLayout.setOnClickListener {
            Toast.makeText(context,"Working",Toast.LENGTH_LONG)
                .show()
        }

        cityLayout.setOnClickListener {
            Toast.makeText(context,"Working",Toast.LENGTH_LONG)
                .show()
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
}