package com.app.themoviedb.ui.home.adapter

import androidx.fragment.app.Fragment

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.themoviedb.ui.home.fragments.NowPlayingMoviesFragment
import com.app.themoviedb.ui.home.fragments.PopularMoviesFragment
import com.app.themoviedb.ui.home.fragments.TopRatedMoviesFragment
import com.app.themoviedb.ui.home.fragments.UpcomingMoviesFragment
import com.app.themoviedb.utils.Constants

class MovieViewPagerAdapter(fm : Fragment) : FragmentStateAdapter(fm)  {

    private val homeTabFragments: Map<Int, () -> Fragment> = mapOf(
        Constants.NOW_PLAYING_MOVIE_INDEX to { NowPlayingMoviesFragment() },
        Constants.POPULAR_MOVIE_INDEX to { PopularMoviesFragment() },
        Constants.TOP_RATED_MOVIE_INDEX to { TopRatedMoviesFragment() },
        Constants.UPCOMING_MOVIE_INDEX to { UpcomingMoviesFragment() }
    )

    override fun getItemCount() = homeTabFragments.size

    override fun createFragment(position: Int): Fragment {
        return homeTabFragments[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

}