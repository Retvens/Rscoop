package com.retvence.rscoop.DashBoardClient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.ExploreFragment
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.ProfileFragment
import com.retvence.rscoop.DashBoardClient.ClientNavigation.ClientDashboardFragment
import com.retvence.rscoop.DashBoardClient.ClientNavigation.ClientFavoriteFragment
import com.retvence.rscoop.DashBoardClient.ClientNavigation.ClientProfileFragment
import com.retvence.rscoop.DashBoardClient.ClientNavigation.ClientTodoFragment
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.BookmarkFragment
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.TodoFragment
import com.retvens.rscoop.R

class ClientDashboardActivity : AppCompatActivity() {

    private  val clientDashboardFragment = ClientDashboardFragment()
    private val clientTodoFragment = ClientTodoFragment()
    private val clientFavoriteFragment = ClientFavoriteFragment()
    private val clientProfileFragment = ClientProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_dashboard)

        replaceFragment(ClientDashboardFragment())

        val bottom_Nav = findViewById<BottomNavigationView>(R.id.nav_Bottom_client)

        bottom_Nav.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.explore_client -> replaceFragment(clientDashboardFragment)
                R.id.calender_client -> replaceFragment(clientTodoFragment)
                R.id.favorite_client -> replaceFragment(clientFavoriteFragment)
                R.id.profile_client -> replaceFragment(clientProfileFragment)
            }
            true
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        if (fragment !=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_client,fragment)
            transaction.commit()
        }
    }
}