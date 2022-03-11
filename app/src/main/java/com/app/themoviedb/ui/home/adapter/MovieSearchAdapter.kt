package com.app.themoviedb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.themoviedb.base.BaseViewHolder
import com.app.themoviedb.databinding.SearchItemBinding
import com.app.themoviedb.helpers.OnItemClickListener
import com.app.themoviedb.models.SearchResultData
import com.app.themoviedb.repository.api.MoviesDbApiService
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class MovieSearchAdapter(private val onItemClickListener: OnItemClickListener) :
    PagingDataAdapter<SearchResultData, MovieSearchAdapter.ViewHolder>(COMPARATOR
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindItem(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: SearchItemBinding) :
        BaseViewHolder<SearchResultData>(binding.root) {

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


        override fun bindItem(item: SearchResultData) {
            binding.apply {
                itemMoreName.text = item.title
                itemMoreSubtitle.text = item.originalTitle
                Glide.with(itemView)
                    .load(MoviesDbApiService.IMAGE_BASE_URL+item.posterPath)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemMoreImage)
            }
        }
    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<SearchResultData>() {
            override fun areItemsTheSame(
                oldItem: SearchResultData,
                newItem: SearchResultData
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: SearchResultData,
                newItem: SearchResultData
            ): Boolean = oldItem.id == newItem.id

        }

    }

    }

