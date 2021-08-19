package com.gtechnofy.funtv.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gtechnofy.fun_imageloading.ImageUtil
import com.gtechnofy.funtv.databinding.MovieListItemBinding
import com.gtechnofy.funtv.network.model.Movie

class DiscoveryListAdapter(
    private val movies: MutableList<Movie> = mutableListOf()
) : RecyclerView.Adapter<DiscoveryListAdapter.MovieItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.binding.title.text = movies[position].title
        Glide.with(holder.binding.rootCard)
            .load(ImageUtil.getCompleteUrl(movies[position].imageUrl))
            .into(holder.binding.imageView)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setMoviesList(list: List<Movie>) {
        movies.clear()
        movies.addAll(list)
        notifyDataSetChanged()
    }

    class MovieItemViewHolder(val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}