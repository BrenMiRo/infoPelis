package com.brenmiro.infopelis.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.brenmiro.infopelis.MovieAdapter
import com.brenmiro.infopelis.R
import com.brenmiro.infopelis.data.model.Movie
import com.brenmiro.infopelis.data.model.Movies
import com.brenmiro.infopelis.databinding.ActivityMainBinding
import com.brenmiro.infopelis.ui.viewmodels.MainActivityViewModel
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {
    private val vm: MainActivityViewModel by viewModels()
    private lateinit var adapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var moviesOrderByPopularity : Movies
        vm.findPopularMovies()
        if (vm.moviesMLD.value != null) {
            moviesOrderByPopularity = vm.moviesMLD.value!!
            initRecyclerView(moviesOrderByPopularity)
        }

//        adapter = MovieAdapter(moviesOrderByPopularity)
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter

        setListeners()
        setObservers()
    }

    private fun setObservers() {
        vm.moviesMLD.observe(this,{
            if (it != null){
                //adapter.setData(it)
                initRecyclerView(vm.moviesMLD.value!!)
                binding.tvFilmsList.text = vm.moviesMLD.value!!.results[0].title
            }
        })
        vm.moviesException.observe(this, this::handleException)
        vm.isLoading.observe(this,{
            if (it == true){
                binding.tvFilmsList.text = "Buscando"
            } else {
                binding.tvFilmsList.text = " "            }
        })
    }

    private fun handleException(exception: Throwable?) {
        if (exception is HttpException)
            when (exception.code()) {
                400 -> Toast.makeText(this, R.string.bad_request.toString(), Toast.LENGTH_LONG).show()
                404 -> Toast.makeText(this, R.string.resource_not_found.toString(), Toast.LENGTH_LONG).show()
                in 500..599 -> Toast.makeText(this, R.string.server_error.toString(), Toast.LENGTH_LONG).show()
                else -> Toast.makeText(this, R.string.unknown_error.toString(), Toast.LENGTH_LONG).show()
            }
    }

    private fun setListeners() {
        vm.findPopularMovies()
    }

    private fun initRecyclerView(films: Movies) {
        adapter = MovieAdapter(films)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}