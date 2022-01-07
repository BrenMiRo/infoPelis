package com.brenmiro.infopelis.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brenmiro.infopelis.data.model.Movie
import com.brenmiro.infopelis.data.model.Movies
import com.brenmiro.infopelis.data.repository.MoviesRepository
import com.brenmiro.infopelis.data.repository.MoviesRepositoryImpl
import com.brenmiro.infopelis.data.retrofit.APIService
import com.brenmiro.infopelis.data.retrofit.MoviesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityViewModel  : ViewModel() {
    private val moviesRepository = MoviesRepositoryImpl()
    val moviesMLD = MutableLiveData<Movies>()
    val moviesException = MutableLiveData<Throwable>()
    val isLoading = MutableLiveData<Boolean>()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun findPopularMovies(){
        viewModelScope.launch() {
            val call = getRetrofit().create(MoviesApi::class.java).getPopularMovies()
            val films = call.body() as Movies
                if (call.isSuccessful) {
                    moviesMLD.value = films
                //initRecyclerView(films)
                } else {
                    //showErrorDialog()
                }

        }




    //        viewModelScope.launch() {
//            isLoading.value = true
//
//            try {
//                val movies = moviesRepository.searchPopularMovies()
//                if (!movies.isNullOrEmpty()){
//                    moviesMLD.value = movies
//                    isLoading.value = false
//                }
//
//            } catch (e:Exception) {
//                moviesException.value = e
//            }
//
//        }




    }
}