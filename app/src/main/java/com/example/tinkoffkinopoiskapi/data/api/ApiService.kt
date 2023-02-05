package com.example.tinkoffkinopoiskapi.data.api

import android.media.Image
import com.example.tinkoffkinopoiskapi.model.Film
import com.example.tinkoffkinopoiskapi.model.FilmsCollection
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @Headers(
        "X-API-KEY: 2e2cab7d-d477-4184-9d95-62b382313683",
        "Content-Type: application/json"
    )
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS&page=1")
    suspend fun getPopularFilms(): Response<FilmsCollection>

    @Headers(
        "X-API-KEY: 2e2cab7d-d477-4184-9d95-62b382313683",
        "Content-Type: application/json"
    )
    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmInfo(
        @Path("id") id: Int): Response<Film>

    @GET("/images/posters/kp/{id}.jpg")
    suspend fun getFilmPhoto(
        @Path("id") id: Int
    ): Response<Image>

}