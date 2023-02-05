package com.example.tinkoffkinopoiskapi.ui.popularFilms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffkinopoiskapi.data.api.FilmApiStatus
import com.example.tinkoffkinopoiskapi.data.repository.FilmRepository
import com.example.tinkoffkinopoiskapi.model.Film
import kotlinx.coroutines.launch

class PopularFilmsViewModel : ViewModel() {
    val filmList = MutableLiveData<MutableList<Film>>()
    private val repository = FilmRepository

    private val _status = MutableLiveData<FilmApiStatus>()
    val status: LiveData<FilmApiStatus> = _status

    init {
        viewModelScope.launch {
            startLoading()
        }
    }

    suspend fun startLoading(){
        _status.value = FilmApiStatus.LOADING
        try {
            filmList.value = repository.getPopularFilms().body()?.films
            _status.value = FilmApiStatus.DONE
        }
        catch (e: Exception) {
            _status.value = FilmApiStatus.ERROR
            filmList.value = mutableListOf()
        }
    }
}
