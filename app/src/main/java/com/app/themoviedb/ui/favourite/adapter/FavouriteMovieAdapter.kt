package com.app.themoviedb.ui.favourite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.themoviedb.databinding.MovieItemLayoutBinding
import com.app.themoviedb.repository.api.MoviesDbApiService
import com.app.themoviedb.repository.data.local.entity.FavouriteMovies
import com.app.themoviedb.utils.DateUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class FavouriteMovieAdapter : androidx.recyclerview.widget.ListAdapter<FavouriteMovies,FavouriteMovieAdapter.ViewHolder>(DiffCallback()) {



    class ViewHolder(val binding: MovieItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(currentItem: FavouriteMovies?) {
            binding.apply {
                singleItemMovieTitle.text = currentItem?.title
                singleItemMovieRating.rating = currentItem?.voteAvg?.div(2)?.toFloat()!!
                singleItemMovieReleaseDate.text = "Release date: ".plus(DateUtils.getStringDate(currentItem.releaseDate))
                singleItemMovieType.text = "Genre: "+currentItem.genre
                singleItemMovieOverview.text = currentItem.overView
                Glide.with(itemView)
                    .load(MoviesDbApiService.IMAGE_BASE_URL+currentItem.posterPath)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(singleItemMovieImage)
            }

        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    class DiffCallback : DiffUtil.ItemCallback<FavouriteMovies>() {
        override fun areItemsTheSame(oldItem: FavouriteMovies, newItem: FavouriteMovies): Boolean {
           return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FavouriteMovies,
            newItem: FavouriteMovies
        ): Boolean {
           return oldItem == newItem
        }

    }
}