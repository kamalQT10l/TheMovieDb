package com.app.themoviedb.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.themoviedb.R
import com.app.themoviedb.databinding.FragmentHomeBinding
import com.app.themoviedb.ui.home.adapter.MovieViewPagerAdapter
import com.app.themoviedb.utils.Constants
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.pager.adapter = MovieViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        return binding.root
    }


    private fun getTabTitle(position: Int): String? {
        return when (position) {
            Constants.POPULAR_MOVIE_INDEX -> "Popular"
            Constants.TOP_RATED_MOVIE_INDEX -> "Top rated"
            Constants.NOW_PLAYING_MOVIE_INDEX -> "Now Playing"
            Constants.UPCOMING_MOVIE_INDEX -> "Upcoming"
            else -> null
        }
    }

}