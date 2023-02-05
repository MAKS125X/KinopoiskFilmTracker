package com.example.tinkoffkinopoiskapi.model

data class FilmsCollection(
    val films: MutableList<Film>,
    val pagesCount: Int
)