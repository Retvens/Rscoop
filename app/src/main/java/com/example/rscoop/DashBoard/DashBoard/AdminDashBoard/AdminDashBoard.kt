package com.example.rscoop.DashBoard.DashBoard.AdminDashBoard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.ExploreFragment
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.BookmarkFragment
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.TodoFragment
import com.retvens.rscoop.R

//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
class AdminDashBoard : AppCompatActivity() {
//    private lateinit var toggle: ActionBarDrawerToggle
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var hotelRecyclerView:RecyclerView
//    private lateinit var adapter: RecyclerAdminView
//    private lateinit var hotelAdapter:RecyclerHotelsView

    private  val exploreFragment =  ExploreFragment()
    private val todoFragment = TodoFragment()
    private val bookmarkFragment = BookmarkFragment()
    private val profileFragment = ProfileFragment()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dash_board)

            replaceFragment(ExploreFragment())

        val bottom_Nav = findViewById<BottomNavigationView>(R.id.nav_Bottom)

        bottom_Nav.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.explore -> replaceFragment(exploreFragment)
                R.id.calender -> replaceFragment(todoFragment)
                R.id.bookmark -> replaceFragment(bookmarkFragment)
                R.id.Profile -> replaceFragment(profileFragment)
            }
                true
        }


//        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
//        val navView: NavigationView = findViewById(R.id.nav_view)
//
//        toggle = ActionBarDrawerToggle(this,drawerLayout,0,0)
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        val header = LayoutInflater.from(this).inflate(R.layout.homepage,navView,false)
//        navView.addHeaderView(header)




//functions


    }

    private fun replaceFragment(fragment:Fragment) {
            if (fragment !=null){
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container,fragment)
                transaction.commit()
            }
    }


}