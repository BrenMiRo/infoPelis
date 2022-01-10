package com.brenmiro.infopelis.data.retrofit

import com.brenmiro.infopelis.data.model.MovieDetail
import com.brenmiro.infopelis.data.model.Movies
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    private fun getApi(): MoviesApi{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        return retrofit.create(MoviesApi::class.java)
    }

    suspend fun getPopularMovies(): Response<Movies> {
        return getApi().getPopularMovies()
    }
    suspend fun getMovieById(id:Int): Response<MovieDetail>{
        return getApi().getMovieById(id)
    }



}