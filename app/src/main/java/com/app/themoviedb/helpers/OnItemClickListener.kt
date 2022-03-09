package com.app.themoviedb.helpers

import com.app.themoviedb.models.Movies

interface OnItemClickListener {
    fun onItemClick(photo: Movies)
}