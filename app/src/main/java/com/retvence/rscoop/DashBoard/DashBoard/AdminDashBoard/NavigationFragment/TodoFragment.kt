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

    lateinit var viewPagerToDo: ViewPager
    lateinit var tabLayoutT: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            viewPagerToDo = view.findViewById(R.id.view_pager_task)
            tabLayoutT = view.findViewById(R.id.tabs)

            val fragmentAdapterT = FragmentAdapter(requireActivity().supportFragmentManager)
            fragmentAdapterT.addFragment(RecentTasks(), "Recent")
            fragmentAdapterT.addFragment(TodayTasks(), "Today")
            fragmentAdapterT.addFragment(CompletedTasks(), "Completed")

            viewPagerToDo.adapter = fragmentAdapterT
            tabLayoutT.setupWithViewPager(viewPagerToDo)

    }
}