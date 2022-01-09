package com.brenmiro.infopelis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brenmiro.infopelis.data.model.Movie
import com.brenmiro.infopelis.data.model.Movies
import com.brenmiro.infopelis.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(private var movies: Movies, private val listener: IMovieAdapter) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder (private val binding: ItemMovieBinding, private val listener: IMovieAdapter) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            private const val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        }

        fun bind(movie: Movie){
            binding.tvMovieTitle.text = movie.title
            Picasso.get().load(IMAGE_BASE + movie.posterPath).into(binding.ivMovie)
            itemView.setOnClickListener {
                listener.onItemClicked(movie.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies.results[position])
    }

    override fun getItemCount(): Int {
        return movies.results.size
    }

//    fun setData(it: Movies) {
//        this.movies = it
//        this.notifyDataSetChanged()
//    }

}
interface IMovieAdapter {
    fun onItemClicked(id: String)
}
