package com.example.second_app_miniflix

object FavoriteStore {
    private val favorites = mutableListOf<Movie>()

    fun addFavorite(movie: Movie) {
        if (favorites.none { it.title == movie.title }) {
            favorites.add(movie)
        }
    }

    fun removeFavorite(title: String) {
        favorites.removeAll { it.title == title }
    }

    fun getFavorites(): List<Movie> = favorites
}
