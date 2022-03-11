package com.app.themoviedb.ui.home.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.themoviedb.R
import com.app.themoviedb.databinding.FragmentUpcomingBinding
import com.app.themoviedb.helpers.OnItemClickListener
import com.app.themoviedb.models.Movies
import com.app.themoviedb.ui.home.adapter.MoviesLoadStateAdapter
import com.app.themoviedb.ui.home.adapter.UpcomingMoviesAdapter
import com.app.themoviedb.ui.home.viewmodel.UpcomingMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpcomingMoviesFragment: Fragment(R.layout.fragment_upcoming), OnItemClickListener {

    private var binding : FragmentUpcomingBinding? = null
    private val viewModel by viewModels<UpcomingMoviesViewModel>()
    private lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpcomingBinding.bind(view)
        upcomingMoviesAdapter = UpcomingMoviesAdapter(this)
        binding?.apply {
            fragmentUpcomingMoviesRv.setHasFixedSize(true)
            fragmentUpcomingMoviesRv.itemAnimator = null
            fragmentUpcomingMoviesRv.adapter = upcomingMoviesAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter { upcomingMoviesAdapter.retry() },
                footer = MoviesLoadStateAdapter { upcomingMoviesAdapter.retry() }
            )
        }

        lifecycleScope.launch {
            viewModel.getUpcomingMovies().collectLatest {
                upcomingMoviesAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onItemClick(movie: Any) {
        val mov = movie as Movies
        val action = HomeFragmentDirections.actionNowPlayingMovieToDetailsFragment(mov.id)
        findNavController().navigate(action)
    }
}