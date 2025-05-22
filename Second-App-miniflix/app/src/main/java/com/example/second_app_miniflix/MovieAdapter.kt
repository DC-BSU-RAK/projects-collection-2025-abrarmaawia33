package com.example.second_app_miniflix

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(
    private var movieList: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    fun updateData(newList: List<Movie>) {
        movieList = newList
        notifyDataSetChanged()
    }


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImage: ImageView = itemView.findViewById(R.id.posterImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        Log.d("MovieAdapter", "Binding movie: ${movie.title}")
        holder.posterImage.setImageResource(movie.poster)
        holder.itemView.setOnClickListener { onMovieClick(movie) }
    }

    override fun getItemCount(): Int = movieList.size
}
