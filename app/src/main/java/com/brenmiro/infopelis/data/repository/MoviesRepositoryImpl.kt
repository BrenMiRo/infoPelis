package com.brenmiro.infopelis.data.repository

import com.brenmiro.infopelis.data.model.Movies
import com.brenmiro.infopelis.data.retrofit.APIService

class MoviesRepositoryImpl: MoviesRepository {
    override suspend fun searchPopularMovies(): Movies {
        val response = APIService().getPopularMovies()
        return response.body()!!
    }
}