package com.brenmiro.infopelis.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.brenmiro.infopelis.IMovieAdapter
import com.brenmiro.infopelis.MovieAdapter
import com.brenmiro.infopelis.R
import com.brenmiro.infopelis.data.model.Movies
import com.brenmiro.infopelis.databinding.ActivityMainBinding
import com.brenmiro.infopelis.ui.viewmodels.MainActivityViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity(), IMovieAdapter {
    private val vm: MainActivityViewModel by viewModels()
    private lateinit var adapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        vm.findPopularMovies()
    }

    private fun setObservers() {
        vm.moviesMLD.observe(this,{
            if (it != null){
                initRecyclerView(vm.moviesMLD.value!!)
            } else {
                Snackbar.make(binding.root, getString(R.string.there_are_not_movies),LENGTH_INDEFINITE).setAction(getString(
                                    R.string.ok)){}.show()
            }
        })
        vm.moviesException.observe(this, this::handleException)

    }

    private fun handleException(exception: Throwable?) {
        if (exception is HttpException)
            when (exception.code()) {
                400 -> Toast.makeText(this, R.string.bad_request.toString(), Toast.LENGTH_LONG).show()
                404 -> Toast.makeText(this, R.string.resource_not_found.toString(), Toast.LENGTH_LONG).show()
                in 500..599 -> Snackbar .make(binding.root, R.string.server_error.toString(),LENGTH_INDEFINITE)
                                        .setAction("RETRY"){vm.findPopularMovies()}
                                        .show()
                else -> Toast.makeText(this, R.string.unknown_error.toString(), Toast.LENGTH_LONG).show()
            }
        if (exception is IOException)
            Snackbar.make(binding.root,  getString(R.string.without_internet),LENGTH_INDEFINITE)
                    .setAction("RETRY"){vm.findPopularMovies()}
                    .show()
    }

    private fun initRecyclerView(films: Movies) {
        adapter = MovieAdapter(films,this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onItemClicked(id: Int) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra("Id", id)
        startActivity(intent)
        finish()
    }
}