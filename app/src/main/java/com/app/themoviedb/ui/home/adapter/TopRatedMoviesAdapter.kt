package com.app.themoviedb.ui.home.adapter

 import android.util.Log
 import android.view.LayoutInflater
 import android.view.ViewGroup
 import androidx.paging.PagingDataAdapter
 import androidx.recyclerview.widget.RecyclerView
 import com.app.themoviedb.base.BaseViewHolder
 import com.app.themoviedb.base.MoviesDiffUtil
 import com.app.themoviedb.databinding.MovieItemLayoutBinding
 import com.app.themoviedb.helpers.OnItemClickListener
 import com.app.themoviedb.models.Movies
 import com.app.themoviedb.repository.api.MoviesDbApiService
 import com.app.themoviedb.utils.DateUtils
 import com.bumptech.glide.Glide
 import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class TopRatedMoviesAdapter(private val onItemClickListener: OnItemClickListener) :
    PagingDataAdapter<Movies, TopRatedMoviesAdapter.ViewHolder>(
        MoviesDiffUtil
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindItem(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MovieItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: MovieItemLayoutBinding) :
        BaseViewHolder<Movies>(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        onItemClickListener.onItemClick(item)
                    }
                }
            }
        }


        override fun bindItem(item: Movies) {
            if(item == null){
                return
            }else{
                binding.apply {
                    singleItemMovieTitle.text = item.title
                    singleItemMovieRating.rating = item.voteAverage.div(2).toFloat()
                    singleItemMovieReleaseDate.text = "Release date: ".plus(DateUtils.getStringDate(item.releaseDate))
                    singleItemMovieType.text = "Genre: "+item.genreIds
                    singleItemMovieOverview.text = item.overview
                    Log.e("TAG","Image url****"+ MoviesDbApiService.IMAGE_BASE_URL+item.posterPath)
                    Glide.with(itemView)
                        .load(MoviesDbApiService.IMAGE_BASE_URL+item.posterPath)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(com.google.android.material.R.drawable.mtrl_ic_error)
                        .into(singleItemMovieImage)
                }
            }
        }

    }

}