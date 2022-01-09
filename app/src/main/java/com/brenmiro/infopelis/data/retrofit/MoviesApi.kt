package com.brenmiro.infopelis.data.retrofit

import com.brenmiro.infopelis.data.model.MovieDetail
import com.brenmiro.infopelis.data.model.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {

    companion object {
        const val key: String = "d8f108351b6ff2a575af2182e68ded1f"
    }

    @GET("discover/movie?sort_by=popularity.desc&api_key=${key}&language=es")
    suspend fun getPopularMovies(): Response<Movies>

    @GET("movie/{itemId}?api_key=${key}&language=es")
    suspend fun getMovieById(@Path("itemId") id: String): Response<MovieDetail>

}