package com.codigo.tmdb.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codigo.tmdb.ui.movielist.popular.PopularMoviesFragment
import com.codigo.tmdb.ui.movielist.upcoming.UpcomingMoviesFragment


class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle, val totalPage : Int)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return totalPage
    }

    override fun createFragment(position: Int): Fragment {
        return if(position==0){
            PopularMoviesFragment.newInstance()
        }else{
            UpcomingMoviesFragment.newInstance()
        }

    }
}