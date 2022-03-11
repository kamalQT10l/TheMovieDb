package com.app.themoviedb.ui.home.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.themoviedb.R
import com.app.themoviedb.databinding.FragmentPopularBinding
import com.app.themoviedb.helpers.OnItemClickListener
import com.app.themoviedb.models.Movies
import com.app.themoviedb.ui.home.adapter.MoviesLoadStateAdapter
import com.app.themoviedb.ui.home.adapter.PopularMoviesAdapter
import com.app.themoviedb.ui.home.viewmodel.PopularMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment: Fragment(R.layout.fragment_popular), OnItemClickListener {
    private lateinit var binding: FragmentPopularBinding
    private val viewModel by viewModels<PopularMoviesViewModel>()
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPopularBinding.bind(view)
        popularMoviesAdapter = PopularMoviesAdapter(this)

        binding.apply {
                fragmentPopularMoviesRv.setHasFixedSize(true)
                fragmentPopularMoviesRv.itemAnimator = null
                fragmentPopularMoviesRv.adapter = popularMoviesAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter { popularMoviesAdapter.retry() },
                footer = MoviesLoadStateAdapter { popularMoviesAdapter.retry() }
            )
        }

        lifecycleScope.launch {
            viewModel.getPopularMovies().collectLatest {
                popularMoviesAdapter.submitData(it)
            }
        }
    }

    override fun onItemClick(movie: Any) {
        val mov = movie as Movies
        val action = HomeFragmentDirections.actionNowPlayingMovieToDetailsFragment(mov.id)
        findNavController().navigate(action)
    }
}