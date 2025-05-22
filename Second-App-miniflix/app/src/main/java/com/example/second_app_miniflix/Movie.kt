package com.example.second_app_miniflix

data class Movie(
    val poster: Int,
    val title: String,
    val description: String,
    val url: String,
    val movieInfo: String = ""
)
