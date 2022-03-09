package com.app.themoviedb.ui.home.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.themoviedb.R
import com.app.themoviedb.databinding.FragmentNowPlayingBinding
import com.app.themoviedb.helpers.OnItemClickListener
import com.app.themoviedb.models.Movies
import com.app.themoviedb.ui.home.adapter.MoviesLoadStateAdapter
import com.app.themoviedb.ui.home.adapter.NowPlayingAdapter
import com.app.themoviedb.ui.home.viewmodel.NowPlayingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NowPlayingMoviesFragment: Fragment(R.layout.fragment_now_playing), OnItemClickListener {

    private lateinit var binding: FragmentNowPlayingBinding
    private val viewModel by viewModels<NowPlayingViewModel>()
    private lateinit var nowPlayingAdapter: NowPlayingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNowPlayingBinding.bind(view)
        nowPlayingAdapter = NowPlayingAdapter(this)
        binding.apply {
            fragmentNowShowingMoviesRv.setHasFixedSize(true)
            fragmentNowShowingMoviesRv.itemAnimator = null
            fragmentNowShowingMoviesRv.adapter = nowPlayingAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter { nowPlayingAdapter.retry() },
                footer = MoviesLoadStateAdapter { nowPlayingAdapter.retry() }
            )
        }


        lifecycleScope.launch {
            viewModel.getNowPlayingViewModel().collectLatest {
                nowPlayingAdapter.submitData(it)
            }
        }
    }

    override fun onItemClick(photo: Movies) {
    }


}