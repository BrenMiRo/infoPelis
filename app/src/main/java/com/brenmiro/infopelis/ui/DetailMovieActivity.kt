package com.brenmiro.infopelis.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import com.brenmiro.infopelis.R
import com.brenmiro.infopelis.data.model.Genre
import com.brenmiro.infopelis.databinding.ActivityDetailMovieBinding
import com.brenmiro.infopelis.ui.viewmodels.DetailMovieActivityViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import retrofit2.HttpException
import java.io.IOException

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

        val movieId = intent.extras!!.getInt("Id")
        setListeners(movieId)
        setObservers(movieId)
    }

    private fun setObservers(id: Int?) {
        vm.movieMLD.observe(this,{
            if (it != null){
                binding.tvTitle.text = vm.movieMLD.value!!.title
                binding.tvOriginalTitle.text = "TITULO ORIGINAL: ${vm.movieMLD.value!!.originalTitle}"
                binding.tvGenre.text = getGenresString(vm.movieMLD.value!!.genre)
                binding.tvReleaseDate.text = "ESTRENO: ${getDate(vm.movieMLD.value!!.releaseDate)}"
                binding.tvOverview.text = vm.movieMLD.value!!.overview
                binding.tvVoteAverage.text = "PUNTUACIÓN: ${vm.movieMLD.value!!.voteAverage}/10"
                showImage(binding.ivBackdrop,vm.movieMLD.value!!.backdropPath,vm.movieMLD.value!!.posterPath)
                showImage(binding.ivPosterPath, vm.movieMLD.value!!.posterPath, vm.movieMLD.value!!.backdropPath)
            }
        })
        vm.moviesException.observe(this, {
            handleException(it,id)
        })
    }

    private fun handleException(exception: Throwable?,id: Int?) {
        if (exception is HttpException)
            when (exception.code()) {
                400 -> Toast.makeText(this, R.string.bad_request.toString(), Toast.LENGTH_LONG).show()
                404 -> Toast.makeText(this, R.string.resource_not_found.toString(), Toast.LENGTH_LONG).show()
                in 500..599 -> Snackbar .make(binding.root, R.string.server_error.toString(), LENGTH_INDEFINITE)
                    .setAction("RETRY"){
                        if (id != null) vm.findMovie(id)
                    }
                    .show()
                else -> Toast.makeText(this, R.string.unknown_error.toString(), Toast.LENGTH_LONG).show()
            }
        if (exception is IOException)
            Snackbar.make(binding.root, "Verifique su conexión a internet", LENGTH_INDEFINITE)
                .setAction("RETRY"){
                    if (id != null) vm.findMovie(id)
                }
                .show()
    }

    private fun showImage(imageView: ImageView, pathPrincipal: String?, pathAlternative: String?) {
        if (pathPrincipal != null){
            Picasso .get()
                .load(IMAGE_BASE + pathPrincipal)
                .into(imageView)
        } else if (pathAlternative != null){
            Picasso .get()
                .load(IMAGE_BASE + pathAlternative)
                .into(imageView)
        }    }

    private fun getDate(date: String): String {
        val yyyy = "${date[0]}${date[1]}${date[2]}${date[3]}"
        val mm = "${date[5]}${date[6]}"
        val dd = "${date[8]}${date[9]}"

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

    private fun setListeners(id:Int?) {
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