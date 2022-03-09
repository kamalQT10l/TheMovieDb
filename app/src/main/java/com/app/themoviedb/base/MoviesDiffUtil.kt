package com.app.themoviedb.base

import androidx.recyclerview.widget.DiffUtil
import com.app.themoviedb.models.Movies

object MoviesDiffUtil : DiffUtil.ItemCallback<Movies>() {


    override fun areItemsTheSame(
        oldItem: Movies,
        newItem: Movies
    ): Boolean = oldItem == newItem


    override fun areContentsTheSame(
        oldItem: Movies,
        newItem: Movies
    ): Boolean = oldItem.id == newItem.id

}