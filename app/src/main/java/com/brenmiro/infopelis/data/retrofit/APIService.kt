package com.brenmiro.infopelis.data.retrofit

import com.brenmiro.infopelis.data.model.Movies
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    companion object {
        const val key: String = "d8f108351b6ff2a575af2182e68ded1f"
    }

    @GET("discover/movie?sort_by=popularity.desc&api_key=$key")
    suspend fun getPopularMovies (): Response<Movies>

}