package com.example.second_app_miniflix

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MovieDetailScreen : AppCompatActivity() {

    private lateinit var heartButton: ImageButton
    private lateinit var sharedPrefs: SharedPreferences

    private lateinit var movie: Movie
    private var movieTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail_screen)

        // UI references
        heartButton = findViewById(R.id.favoriteButton)
        val posterImage = findViewById<ImageView>(R.id.moviePoster)
        val titleText = findViewById<TextView>(R.id.movieTitle)
        val descText = findViewById<TextView>(R.id.movieDescription)
        val watchButton = findViewById<ImageButton>(R.id.watchButton)
        val movieInfoTextView = findViewById<TextView>(R.id.movieInfoTextView)
        val backButton = findViewById<ImageView>(R.id.backButton)

        // Get movie data from intent
        val poster = intent.getIntExtra("poster", 0)
        val description = intent.getStringExtra("description") ?: "No description"
        val info = intent.getStringExtra("info") ?: "No info"
        val url = intent.getStringExtra("url") ?: "https://www.netflix.com"
        movieTitle = intent.getStringExtra("title") ?: "No title"

        // Create movie object
        movie = Movie(
            title = movieTitle,
            poster = poster,
            description = description,
            movieInfo = info,
            url = url
        )

        // Set views
        posterImage.setImageResource(poster)
        titleText.text = movieTitle
        descText.text = description
        movieInfoTextView.text = info

        // Initialize SharedPreferences
        sharedPrefs = getSharedPreferences("favorites", MODE_PRIVATE)

        val isFav = sharedPrefs.contains(movieTitle)
        if (!isFav) {
            updateHeartIcon(false)  // show unfilled heart
        } else {
            updateHeartIcon(true)   // show filled heart
        }


        // Click: toggle favorite
        heartButton.setOnClickListener {
            toggleFavorite()
        }

        // Watch button click
        watchButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        // Back button click
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }

    private fun saveToFavorites() {
        // Save to SharedPreferences
        val editor = sharedPrefs.edit()
        val movieData = "${movie.poster}|${movie.description}|${movie.url}|${movie.movieInfo}"
        editor.putString(movieTitle, movieData)
        editor.apply()

        // Save to in-memory store
        FavoriteStore.addFavorite(movie)
    }

    private fun toggleFavorite() {
        val editor = sharedPrefs.edit()
        val isFavorite = sharedPrefs.contains(movieTitle)

        if (isFavorite) {
            editor.remove(movieTitle)
            FavoriteStore.removeFavorite(movieTitle)
            updateHeartIcon(false)
        } else {
            saveToFavorites()
            updateHeartIcon(true)
        }

        editor.apply()
    }

    private fun updateHeartIcon(isFavorite: Boolean) {
        val iconRes = if (isFavorite) R.drawable.heartfiled else R.drawable.heartt
        heartButton.setImageResource(iconRes)
    }
}
