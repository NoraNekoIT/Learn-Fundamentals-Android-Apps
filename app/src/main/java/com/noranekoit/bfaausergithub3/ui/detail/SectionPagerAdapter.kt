package com.noranekoit.bfaausergithub3.ui.detail


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.noranekoit.bfaausergithub3.ui.detail.followers.FollowersFragment
import com.noranekoit.bfaausergithub3.ui.detail.following.FollowingFragment
import com.noranekoit.bfaausergithub3.ui.detail.repository.RepositoryFragment

class SectionsPagerAdapter(activity: AppCompatActivity, data :Bundle) : FragmentStateAdapter(activity) {
        private var fragmentBundle: Bundle = data

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = RepositoryFragment()
            2 -> fragment = FollowingFragment()

        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 3
    }
}