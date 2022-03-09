package com.app.themoviedb.ui.home.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.themoviedb.R
import com.app.themoviedb.databinding.FragmentPopularBinding
import com.app.themoviedb.ui.home.adapter.MoviesLoadStateAdapter
import com.app.themoviedb.ui.home.adapter.PopularMoviesAdapter
import com.app.themoviedb.ui.home.viewmodel.PopularMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment: Fragment(R.layout.fragment_popular) {
    private lateinit var binding: FragmentPopularBinding
    private val viewModel by viewModels<PopularMoviesViewModel>()
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPopularBinding.bind(view)
        popularMoviesAdapter = PopularMoviesAdapter()

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
}