package com.example.tinkoffkinopoiskapi.ui.filmInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffkinopoiskapi.data.api.FilmApiStatus
import com.example.tinkoffkinopoiskapi.data.repository.FilmRepository
import com.example.tinkoffkinopoiskapi.model.Country
import com.example.tinkoffkinopoiskapi.model.Genre
import kotlinx.coroutines.launch

class FilmInfoViewModel(private val filmId: Int) : ViewModel() {
    private val repository = FilmRepository
    val genres = MutableLiveData<List<Genre>>()
    val countries = MutableLiveData<List<Country>>()
    val posterUrl = MutableLiveData<String?>()
    val description = MutableLiveData<String?>()
    val name = MutableLiveData<String?>()

    private val _status = MutableLiveData<FilmApiStatus>()
    val status: LiveData<FilmApiStatus> = _status

    init {
        viewModelScope.launch {
            startLoading()
        }
    }

    suspend fun startLoading() {
        _status.value = FilmApiStatus.LOADING
        try {
            val film = repository.getFilmInfo(filmId).body()
            name.value = film?.nameRu
            genres.value = film?.genres
            countries.value = film?.countries
            posterUrl.value = film?.posterUrl
            description.value = film?.description
            _status.value = FilmApiStatus.DONE
        } catch (e: Exception) {
            _status.value = FilmApiStatus.ERROR
            name.value = ""
            genres.value = mutableListOf()
            countries.value = mutableListOf()
            posterUrl.value = ""
            description.value = ""
        }
    }
}