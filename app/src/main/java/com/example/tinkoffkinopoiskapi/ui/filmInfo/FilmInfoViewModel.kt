package com.example.tinkoffkinopoiskapi.ui.filmInfo

import android.media.Image
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffkinopoiskapi.data.repository.FilmRepository
import com.example.tinkoffkinopoiskapi.model.Country
import com.example.tinkoffkinopoiskapi.model.Genre
import kotlinx.coroutines.launch

class FilmInfoViewModel(filmId: Int) : ViewModel() {
    private val id = filmId
    private val repository = FilmRepository

    val genres = MutableLiveData<List<Genre>>()
    val countries = MutableLiveData<List<Country>>()
    val posterUrl = MutableLiveData<String?>()
    val description = MutableLiveData<String?>()
    val name = MutableLiveData<String?>()
    val photo = MutableLiveData<Image>()

    init {
        viewModelScope.launch {
            val film = repository.getFilmInfo(filmId).body()
            name.value = film?.nameRu
            genres.value = film?.genres
            countries.value = film?.countries
            countries.value = film?.countries
            posterUrl.value = film?.posterUrl
            description.value = film?.description
        }
    }
}