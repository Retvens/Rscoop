package com.retvens.rscoop.OnBoardingScreen


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.retvence.rscoop.OnBoardingScreen.OnBoardingFragment2


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

