package com.app.themoviedb.ui.home.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.themoviedb.R
import com.app.themoviedb.databinding.FragmentHomeBinding
import com.app.themoviedb.ui.home.adapter.MovieViewPagerAdapter
import com.app.themoviedb.utils.Constants
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.pager.adapter = MovieViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        setHasOptionsMenu(true)
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                redirectSearchFragment()
                true
            }
            R.id.action_favourites -> {
                redirectFavouriteFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun redirectFavouriteFragment() {
        val action = HomeFragmentDirections.actionHomeToFavFragment()
        findNavController().navigate(action)
    }

    private fun redirectSearchFragment() {
        val action = HomeFragmentDirections.actionHomeToSearchFragment()
        findNavController().navigate(action)
    }


}