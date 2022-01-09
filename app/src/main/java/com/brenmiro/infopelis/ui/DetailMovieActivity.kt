package com.brenmiro.infopelis.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.brenmiro.infopelis.data.model.Genre
import com.brenmiro.infopelis.databinding.ActivityDetailMovieBinding
import com.brenmiro.infopelis.ui.viewmodels.DetailMovieActivityViewModel
import com.squareup.picasso.Picasso

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        private const val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
    }

    private lateinit var binding: ActivityDetailMovieBinding
    private val vm: DetailMovieActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.extras!!.getString("Id")

        setListeners(movieId)
        setObservers()
    }

    private fun setObservers() {
        vm.movieMLD.observe(this,{
            if (it != null){
                binding.tvTitle.text = vm.movieMLD.value!!.title
                binding.tvOriginalTitle.text = "TITULO ORIGINAL: ${vm.movieMLD.value!!.originalTitle}"
                binding.tvGenre.text = getGenresString(vm.movieMLD.value!!.genre)
                binding.tvReleaseDate.text = "ESTRENO: ${getDate(vm.movieMLD.value!!.releaseDate)}"
                //binding.tvReleaseDate.text = "ESTRENO: ${vm.movieMLD.value!!.releaseDate}"
                binding.tvOverview.text = vm.movieMLD.value!!.overview
                binding.tvVoteAverage.text = "PUNTUACIÓN: ${vm.movieMLD.value!!.voteAverage}/10"

                Picasso .get()
                        .load(IMAGE_BASE + vm.movieMLD.value!!.backdropPath)
                        .into(binding.ivBackdrop)

                Picasso .get()
                        .load(IMAGE_BASE + vm.movieMLD.value!!.posterPath)
                        .into(binding.ivPosterPath)
            }
        })
    }

    private fun getDate(date: String): String {
        //yyyy-mm-dd
        var yyyy = "${date[0]}${date[1]}${date[2]}${date[3]}"
        var mm = "${date[5]}${date[6]}"
        var dd = "${date[8]}${date[9]}"

        return "${dd}-${mm}-${yyyy}"

    }

    private fun getGenresString(genre: List<Genre>): String {
        var genresString = "GENERO: "
        for (pos in genre.indices) {
            genresString += if (pos == 0){
                genre[pos].name
            }else{
                ", ${genre[pos].name}"
            }
        }
        return genresString
    }

    private fun setListeners(id:String?) {
        if (id != null) {
            vm.findMovie(id)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}