package com.david.githubmobile.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.david.githubmobile.ui.view.detail.follower.FollowerFragment
import com.david.githubmobile.ui.view.detail.following.FollowingFragment
import com.david.githubmobile.utils.Constant.TAB_TITLES

class DetailAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment.getInstance(username)
            1 -> fragment = FollowingFragment.getInstance(username)
        }
        return fragment as Fragment
    }
}