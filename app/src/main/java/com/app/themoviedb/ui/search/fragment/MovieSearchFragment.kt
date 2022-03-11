package com.app.themoviedb.ui.search.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.themoviedb.R
import com.app.themoviedb.databinding.FragmentSearchBinding
import com.app.themoviedb.helpers.OnItemClickListener
import com.app.themoviedb.models.SearchResultData
import com.app.themoviedb.ui.home.adapter.MovieSearchAdapter
import com.app.themoviedb.ui.home.adapter.MoviesLoadStateAdapter
import com.app.themoviedb.ui.search.viewmodel.MovieSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieSearchFragment : Fragment(R.layout.fragment_search), OnItemClickListener {

    private var binding : FragmentSearchBinding?=null
    private val viewModel by viewModels<MovieSearchViewModel>()
    private lateinit var movieSearchAdapter: MovieSearchAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        setHasOptionsMenu(true)
        movieSearchAdapter = MovieSearchAdapter(this)
        binding?.apply {
            searchRv.setHasFixedSize(true)
            searchRv.itemAnimator = null
            searchRv.adapter = movieSearchAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter { movieSearchAdapter.retry() },
                footer = MoviesLoadStateAdapter { movieSearchAdapter.retry() }
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_only_menu, menu)
        val searchItem = menu.findItem(R.id.action_search_ic)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("TAG","*****onQueryTextSubmit**********"+query)
                if (query != null) {
                    Log.e("TAG","*****onQueryTextSubmit*non null*********"+query)
                    binding?.searchRv?.scrollToPosition(0)
                    lifecycleScope.launch {
                        viewModel.searchMovies(query).collectLatest {
                            Log.e("TAG","****searchMovies***resp******")
                            movieSearchAdapter.submitData(it)
                        }
                    }
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    override fun onItemClick(mov: Any) {
        val movie = mov as SearchResultData
        val action = MovieSearchFragmentDirections.actionSearchToDetailFragment(movie.id)
        findNavController().navigate(action)
    }

}