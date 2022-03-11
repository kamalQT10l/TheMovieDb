package com.app.themoviedb.ui.details.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.app.themoviedb.R
import com.app.themoviedb.databinding.FragmentMovieDetailsBinding
import com.app.themoviedb.models.MovieDetailResponse
import com.app.themoviedb.ui.details.viewmodel.MovieDetailsViewModel
import com.app.themoviedb.utils.DateUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private var binding : FragmentMovieDetailsBinding? = null

    private val viewModel by viewModels<MovieDetailsViewModel>()

    private val args by navArgs<MovieDetailsFragmentArgs>()

    private lateinit var title:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        binding  = FragmentMovieDetailsBinding.bind(view)

        (requireActivity() as AppCompatActivity). setSupportActionBar(binding?.activityDetailToolbar)
        initCollapsingToolbar()
        viewModel.movieDetailLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                configureViews(it)
            }
        })
        val id  = args.movieId
        viewModel.getMovieDetail(id)


    }


    private fun initCollapsingToolbar() {
        binding?.activityDetailCollapsingLayout?.title = " "
        binding?.activityDetailAppBarLayout?.setExpanded(true)
        binding?.activityDetailAppBarLayout?.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding?.activityDetailCollapsingLayout?.title = title
                    isShow = true
                    binding?.activityDetailToolbar?.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                } else if (isShow) {
                    binding?.activityDetailCollapsingLayout?.title = " "
                    isShow = false
                }
            }
        })
        binding?.activityDetailToolbar?.setNavigationOnClickListener {

        }
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun configureViews(it: MovieDetailResponse) {
        binding?.apply {
            title = it.title
            activityDetailMovieTitle.text = it.title
            activityDetailMovieDate.text = DateUtils.getStringDate(it.releaseDate)


            if (it.runtime == 0) activityDetailMovieRunTime.text = "runtime: unavailable"
            else activityDetailMovieRunTime.text = "runtime: "+it.runtime+"mins"

            if (it.budget == 0) activityDetailMovieBudget.text = "budget: unavailable"
            else activityDetailMovieBudget.text = "budget: $"+(it.budget.div(1000))+"k"


            Log.d("TAG", "backdropUrl: ---"+it.backdropUrl)

            Log.d("TAG", "posterImageUrl: ---"+it.posterImageUrl)
            Glide.with(this@MovieDetailsFragment)
                .load(it.backdropUrl)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(activityDetailBackdropImage)

            Glide.with(this@MovieDetailsFragment)
                .load(it.posterImageUrl)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(activityDetailPosterImage)

            if(it.adult){
                activityDetailAdult.text = "Adult 18+"
            }else{
                activityDetailAdult.text = "Non Adult"
            }

            activityDetailVoteAverage.text = "rating: "+it.voteAverage.toString()+"/10"

            if (it.voteCount >= 1000) activityDetailVoteCount.text = "votes: "+("%.2f".format(it.voteCount.toFloat().div(1000)))+"k"
            else activityDetailVoteCount.text = "votes: "+it.voteCount.toString()

            activityDetailOverview.text = it.overview

            activityDetailRatingBar.rating = it.voteAverage.div(2).toFloat()

            tvTagLine.text = it.tagline

            genreLayout.weightSum = it.genres.size.toFloat()



            for (genre in it.genres){
                val factory = LayoutInflater.from(activity)
                val myView: View = factory.inflate(R.layout.item_genre, null)
                val tv = myView.findViewById<TextView>(R.id.genre1)
                val lv = myView.findViewById<LinearLayout>(R.id.constraint_genre_layout)

                val param = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
                )
                param.setMargins(10, 0, 10, 0)
                lv.layoutParams = param

                tv.text = genre.name

                genreLayout.addView(myView)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding= null
    }
}