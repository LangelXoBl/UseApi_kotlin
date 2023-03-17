package com.example.testandroid.data.remote

import com.example.testandroid.data.model.GetMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<GetMoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedmovies(@Query("api_key") apiKey: String): Response<GetMoviesResponse>

    @GET("discover/movie")
    suspend fun getFamilyMovies(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int
    ): Response<GetMoviesResponse>
}