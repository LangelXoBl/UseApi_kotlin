package com.example.testandroid.data.repository

import com.example.testandroid.data.local.MovieDao
import com.example.testandroid.data.model.Movie
import com.example.testandroid.data.model.MovieType
import com.example.testandroid.data.model.toMovieEntityList
import com.example.testandroid.data.remote.RemoteDataSource
import javax.inject.Inject
import com.example.testandroid.utils.performGetOperation

class MovieRepository @Inject constructor(
    private val localDataSource: MovieDao,
    private val remoteDataSource: RemoteDataSource) {


    fun getPopularMovies() = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies(MovieType.POPULAR.value) },
        networkCall = { remoteDataSource.getPopularMovies() },
        saveCallResult = { localDataSource.insertAll(it.results.toMovieEntityList(MovieType.POPULAR.value)) }
    )

    fun getTopRatedMovies() = performGetOperation(
        databaseQuery = {localDataSource.getAllMovies(MovieType.TOPRATED.value)},
        networkCall = {remoteDataSource.getTopRatedMovies()},
        saveCallResult = {localDataSource.insertAll(it.results.toMovieEntityList(MovieType.TOPRATED.value))}

    )

    fun getFamilyMovies() = performGetOperation(
        databaseQuery = {localDataSource.getAllMovies(MovieType.FAMILY.value)},
        networkCall = {remoteDataSource.getFamilyMovies()},
        saveCallResult = {localDataSource.insertAll(it.results.toMovieEntityList(MovieType.FAMILY.value))}
    )
}