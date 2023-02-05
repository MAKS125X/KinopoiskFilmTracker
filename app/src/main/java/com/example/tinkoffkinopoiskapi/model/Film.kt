package com.example.tinkoffkinopoiskapi.model

data class Film(
    val filmId: Int,
    val completed: Boolean,
    val countries: List<Country>,
    val coverUrl: String,
    val description: String,
    val editorAnnotation: String,
    val endYear: Int,
    val filmLength: String,
//    val genres: List<Genre>,
    val has3D: Boolean,
    val hasImax: Boolean,
    val imdbId: String,
    val isTicketsAvailable: Boolean,
    val kinopoiskId: Int,
    val lastSync: String,
    val logoUrl: String,
    val nameEn: String,
    val nameOriginal: String,
//    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val productionStatus: String,
    val ratingAgeLimits: String,
    val ratingAwait: Double,
    val ratingAwaitCount: Int,
    val ratingFilmCritics: Double,
    val ratingFilmCriticsVoteCount: Int,
    val ratingGoodReview: Double,
    val ratingGoodReviewVoteCount: Int,
    val ratingImdb: Double,
    val ratingImdbVoteCount: Int,
    val ratingKinopoisk: Double,
    val ratingKinopoiskVoteCount: Int,
    val ratingMpaa: String,
    val ratingRfCritics: Double,
    val ratingRfCriticsVoteCount: Int,
    val reviewsCount: Int,
    val serial: Boolean,
    val shortDescription: String,
    val shortFilm: Boolean,
    val slogan: String,
    val startYear: Int,
    val type: String,
    val webUrl: String,
    val year: Int,
    val nameRu: String,
    val genres: List<Genre>,
    var isFavourite: Boolean = false
) {

    fun getShortenedNameRu(): String {
        return if (nameRu.length <= 20) nameRu else "${nameRu.slice(0..19)}..."
    }

    fun getInfoAboutFilm(): String {
        val genreString = if (this.genres.isEmpty()) "Жанр не указан"
            else this.genres[0].toString().replaceFirstChar { it.uppercase() }
        val yearString = if (this.year == 0) "" else "${this.year}"
        return "$genreString $yearString"
    }
}