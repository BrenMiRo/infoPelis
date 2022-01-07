package com.brenmiro.infopelis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brenmiro.infopelis.data.model.Movie
import com.brenmiro.infopelis.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    class ViewHolder (private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            private const val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        }

        fun bind(movie: Movie){
            binding.tvMovieTitle.text = movie.title
            Picasso.get().load(IMAGE_BASE + movie.posterPath).into(binding.ivMovie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}