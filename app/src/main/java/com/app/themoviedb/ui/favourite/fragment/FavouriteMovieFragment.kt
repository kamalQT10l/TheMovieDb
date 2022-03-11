package com.app.themoviedb.ui.favourite.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.app.themoviedb.R
import com.app.themoviedb.databinding.FragmentFavouriteBinding
import com.app.themoviedb.ui.favourite.adapter.FavouriteMovieAdapter
import com.app.themoviedb.ui.favourite.viewmodel.FavouriteMoviesViewModel
import com.app.themoviedb.ui.home.adapter.MoviesLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteMovieFragment : Fragment(R.layout.fragment_favourite){
    private val viewModel by viewModels<FavouriteMoviesViewModel>()
    private var binding : FragmentFavouriteBinding?=null
    private lateinit var favouriteMovieAdapter : FavouriteMovieAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouriteBinding.bind(view)
        favouriteMovieAdapter = FavouriteMovieAdapter()

        binding?.apply {
            favouritesRv.setHasFixedSize(true)
            favouritesRv.itemAnimator = null
            favouritesRv.adapter = favouriteMovieAdapter
        }

        viewModel.favMovies.observe(viewLifecycleOwner, Observer {
            favouriteMovieAdapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}