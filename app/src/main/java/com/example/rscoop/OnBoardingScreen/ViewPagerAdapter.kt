package com.retvens.rscoop.OnBoardingScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.rscoop.OnBoardingScreen.OnBoardingFragment2


class ViewPagerAdapter(fragmentManager:FragmentManager) :FragmentPagerAdapter(fragmentManager) {

    val fragment = listOf(
        OnBoardingFragment1(),
        OnBoardingFragment2(),
        OnBoardingFragment3()
    )

    override fun getCount(): Int {
        return fragment.size
    }

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    fun next (position: Int):Fragment{
        return fragment[position]
    }

}

