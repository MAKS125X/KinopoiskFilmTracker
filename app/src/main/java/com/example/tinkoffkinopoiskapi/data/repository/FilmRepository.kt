package com.example.tinkoffkinopoiskapi.data.repository

import android.util.Log
import com.example.tinkoffkinopoiskapi.data.api.RetrofitInstance
import com.example.tinkoffkinopoiskapi.model.Film
import com.example.tinkoffkinopoiskapi.model.FilmsCollection
import retrofit2.Response

object FilmRepository {

    suspend fun getPopularFilms(): Response<FilmsCollection> {
        return RetrofitInstance.api.getPopularFilms()
    }

    suspend fun getFilmInfo(id: Int): Response<Film> {
        return RetrofitInstance.api.getFilmInfo(id)
    }
}