package com.brenmiro.infopelis.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brenmiro.infopelis.data.model.MovieDetail
import com.brenmiro.infopelis.data.repository.MoviesRepositoryImpl
import kotlinx.coroutines.launch

class DetailMovieActivityViewModel : ViewModel() {
    private val moviesRepository = MoviesRepositoryImpl()
    val movieMLD = MutableLiveData<MovieDetail>()
    val moviesException = MutableLiveData<Throwable>()

    fun findMovie(id:Int) {
        viewModelScope.launch {
            try {
                movieMLD.value = moviesRepository.searchMovieById(id)
            } catch (e:Exception){
                moviesException.value = e
            }
        }
    }

}