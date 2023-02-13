package com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.FragmentAdapter
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TodayTasks
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.UpcomingTasks
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.CompletedTasks
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.RecentTasks
import com.retvens.rscoop.R


class TodoFragment : Fragment() {

    lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            tabLayout = view.findViewById<TabLayout>(R.id.tabs)

            val tab1 = tabLayout.newTab().setText("Recent")
            val tab2 = tabLayout.newTab().setText("Today")
            val tab3 = tabLayout.newTab().setText("Completed")
            tabLayout.addTab(tab1)
            tabLayout.addTab(tab2)
            tabLayout.addTab(tab3)

            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RecentTasks())
                .commit()

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val fragment = when (tab.position) {
                        0 -> RecentTasks()
                        1 -> TodayTasks()
                        2 -> CompletedTasks()
                        else -> RecentTasks()
                    }
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            // Set the initial tab selection
            tabLayout.getTabAt(0)?.select()



    }
}