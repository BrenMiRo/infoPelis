package com.brenmiro.infopelis.data.repository

import com.brenmiro.infopelis.data.model.MovieDetail
import com.brenmiro.infopelis.data.model.Movies
import com.brenmiro.infopelis.data.retrofit.APIService

class MoviesRepositoryImpl: MoviesRepository {
    override suspend fun searchPopularMovies(): Movies {
        val response = APIService().getPopularMovies()
        return response.body()!!
    }

    override suspend fun searchMovieById(id:Int): MovieDetail {
        val response = APIService().getMovieById(id)
        return response.body()!!
    }
}