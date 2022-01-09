package com.brenmiro.infopelis.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brenmiro.infopelis.data.model.Movies
import com.brenmiro.infopelis.data.repository.MoviesRepositoryImpl
import com.brenmiro.infopelis.data.retrofit.MoviesApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityViewModel  : ViewModel() {
    private val moviesRepository = MoviesRepositoryImpl()
    val moviesMLD = MutableLiveData<Movies>()
    val moviesException = MutableLiveData<Throwable>()

    fun findPopularMovies(){
        viewModelScope.launch {
            try {
                moviesMLD.value = moviesRepository.searchPopularMovies()
            }catch (e:Exception){
                moviesException.value = e
            }
        }
    }
}