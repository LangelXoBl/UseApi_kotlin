package com.example.testandroid.ui.trendingWeek

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testandroid.data.entities.MovieEntity
import com.example.testandroid.data.repository.MovieRepository
import com.example.testandroid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {
    val fetchTopRatedMovies: LiveData<Resource<List<MovieEntity>>> = repository.getTopRatedMovies()
}