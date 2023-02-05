package com.example.tinkoffkinopoiskapi.ui.filmInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FilmInfoFactory(private val filmId: Int?): ViewModelProvider.Factory {
    override fun <T:ViewModel> create(modelClass: Class<T>):T{
        if(filmId == null)
            throw java.lang.NullPointerException("Can not found this value")

        //return modelClass.getConstructor(Int::class.java).newInstance(filmId)
        return FilmInfoViewModel(filmId) as T
    }



}