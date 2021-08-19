package com.gtechnofy.funtv.ui

import android.util.Log
import androidx.lifecycle.*
import com.gtechnofy.funtv.network.model.Movie
import com.gtechnofy.funtv.network.util.onFailure
import com.gtechnofy.funtv.network.util.onSuccess
import com.gtechnofy.funtv.usecase.DiscoverMovieUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class HomeFragmentViewModel(
    private val useCase: DiscoverMovieUseCase
) : ViewModel() {

    private val _loadMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val loadMovies: LiveData<List<Movie>> = _loadMovies

    fun loadPage() {
        viewModelScope.launch {
            useCase.execute()
                .onSuccess {
                    _loadMovies.value = it.movies
                }
                .onFailure {

                }
        }
    }

    class HomeFragmentViewModelFactory(
        private val useCase: DiscoverMovieUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)) {
                HomeFragmentViewModel(
                    useCase
                ) as T
            } else {
                throw IllegalArgumentException("Check your passed class name again")
            }
        }
    }
}