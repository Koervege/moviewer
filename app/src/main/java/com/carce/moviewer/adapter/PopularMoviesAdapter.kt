package com.carce.moviewer.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carce.moviewer.databinding.MoviePosterBinding
import com.carce.moviewer.model.Movie
import com.carce.moviewer.networkService.ApiURL
import com.squareup.picasso.Picasso

class PopularMoviesAdapter(private var items: MutableList<Movie>): RecyclerView.Adapter<PopularMoviesAdapter.MovieViewHolder>() {

    var onItemClick: ((Movie) -> Unit)? = null

    fun setItemClick(action: (Movie) -> Unit) {
        this.onItemClick = action
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MoviePosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = items[position]
        Picasso.get().load("${ApiURL.IMAGE_URL}${item.image}").into(holder.itemBinding.movieImage)
        holder.itemBinding.movieImage.contentDescription = "poster for ${item.title}"
    }

    override fun getItemCount() = items.size

    inner class MovieViewHolder(val itemBinding: MoviePosterBinding): RecyclerView.ViewHolder(itemBinding.root) {
        init {
            onItemClick?.let {
                itemBinding.root.setOnClickListener {
                    it(items[adapterPosition])
                }
            }
        }
    }
}
