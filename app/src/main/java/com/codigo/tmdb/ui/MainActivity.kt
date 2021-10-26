package com.codigo.tmdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.codigo.tmdb.R
import com.codigo.tmdb.Utils.Utils
import com.codigo.tmdb.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // databinding
    lateinit var binding : ActivityMainBinding

    lateinit var viewPagerAdapter: ViewPagerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupViewPager()


    }

    private fun setupViewPager(){
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,  lifecycle, Utils.TOTAL_TAB )
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tab, binding.viewPager){ tab, position ->
            tab.text = if(position==0) {
                getString(R.string.popular_movie).toString()
            } else {
                getString(R.string.upcoming_movie)
            }
        }.attach()
    }


}