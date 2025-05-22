package com.example.second_app_miniflix

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class favoritescreen : AppCompatActivity() {

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritescreen)

        // Setup RecyclerView
        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView)
        favoritesRecyclerView.layoutManager = GridLayoutManager(this, 2)

        // Get current favorite movies from FavoriteStore
        val favoriteMovies = FavoriteStore.getFavorites()

        // Adapter setup with click listener to open MovieDetailScreen
        adapter = MovieAdapter(favoriteMovies) { movie ->
            val intent = Intent(this, MovieDetailScreen::class.java)
            intent.putExtra("poster", movie.poster)
            intent.putExtra("title", movie.title)
            intent.putExtra("description", movie.description)
            intent.putExtra("info", movie.movieInfo)
            intent.putExtra("url", movie.url)
            startActivity(intent)
        }
        favoritesRecyclerView.adapter = adapter

        // Back button functionality
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh favorites in case new ones were added
        adapter.updateData(FavoriteStore.getFavorites())
    }
}
