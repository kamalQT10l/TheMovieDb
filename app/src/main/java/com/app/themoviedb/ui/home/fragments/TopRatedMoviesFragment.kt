package com.app.themoviedb.ui.home.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.themoviedb.R
import com.app.themoviedb.databinding.FragmentTopRatedBinding
import com.app.themoviedb.ui.home.adapter.MoviesLoadStateAdapter
import com.app.themoviedb.ui.home.adapter.TopRatedMoviesAdapter
import com.app.themoviedb.ui.home.viewmodel.TopRatedMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopRatedMoviesFragment: Fragment(R.layout.fragment_top_rated) {
    private var binding: FragmentTopRatedBinding? = null
    private val viewModel by viewModels<TopRatedMoviesViewModel>()
    private lateinit var topRatedMoviesAdapter: TopRatedMoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopRatedBinding.bind(view)
        topRatedMoviesAdapter = TopRatedMoviesAdapter()
        binding?.apply {
            fragmentTopRatedMoviesRv.setHasFixedSize(true)
            fragmentTopRatedMoviesRv.itemAnimator = null
            fragmentTopRatedMoviesRv.adapter = topRatedMoviesAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter { topRatedMoviesAdapter.retry() },
                footer = MoviesLoadStateAdapter { topRatedMoviesAdapter.retry() }
            )
        }

        lifecycleScope.launch {
            viewModel.getTopRatedMovies().collectLatest {
                topRatedMoviesAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}