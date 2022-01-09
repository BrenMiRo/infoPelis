package com.brenmiro.infopelis.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brenmiro.infopelis.data.model.Movie
import com.brenmiro.infopelis.data.model.Movies
import com.brenmiro.infopelis.data.retrofit.MoviesApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailMovieActivityViewModel : ViewModel() {

    val movieMLD = MutableLiveData<Movie>()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun findMovie(id:String) {
        viewModelScope.launch() {
            val call = getRetrofit().create(MoviesApi::class.java).getMovieById(id)
            val film = call.body() as Movie
            if (call.isSuccessful) {
                movieMLD.value = film
            }
        }
    }

}