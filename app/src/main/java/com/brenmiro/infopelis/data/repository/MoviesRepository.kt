package com.brenmiro.infopelis.data.repository

import com.brenmiro.infopelis.data.model.MovieDetail
import com.brenmiro.infopelis.data.model.Movies

interface MoviesRepository {
    suspend fun searchPopularMovies(): Movies
    suspend fun searchMovieById(id:Int):MovieDetail
}