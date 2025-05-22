package com.example.second_app_miniflix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var usernameTextView: TextView
    private lateinit var infoButton: ImageButton
    private lateinit var favoritesButton: ImageButton

    private lateinit var allNewMovies: List<Movie>
    private lateinit var allUpcomingMovies: List<Movie>
    private lateinit var allRegularMovies: List<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Link views
        usernameTextView = findViewById(R.id.userNameText)
        infoButton = findViewById(R.id.infoButton)
        favoritesButton = findViewById(R.id.favoritesButton)

        // Receive and display username
        val username = intent.getStringExtra("username") ?: "User"
        usernameTextView.text = username

        // Info button opens instruction popup
        infoButton.setOnClickListener {
            showInstructionPopup()
        }

        // Favorites button opens favorites screen
        favoritesButton.setOnClickListener {
            startActivity(Intent(this, favoritescreen::class.java))
        }

        // Movie data
        allNewMovies = listOf(
            Movie(R.drawable.wolfman, "Wolfman", "A man is afflicted by a mysterious condition and turns into a werewolf.", "https://www.imdb.com/title/tt4216984/", "2025 • Horror-Thriller • TBD"),
            Movie(R.drawable.themonkey, "The Monkey", "A terrifying tale about a killer monkey based on Stephen King's story.", "https://www.imdb.com/title/tt27714946//", "2025 • Horror • TBD"),
            Movie(R.drawable.theelectricstate, "The Electric State", "A teenage girl travels with a robot across a futuristic America.", "https://www.netflix.com/ae-en/title/81601562", "2024 • Sci-Fi-Adventure • 2h1m"),
        )

        allUpcomingMovies = listOf(
            Movie(R.drawable.conjuring, "The Conjuring", "Paranormal investigators Ed and Lorraine Warren try to vanquish a demon from a family's home.", "https://www.netflix.com/cx/title/70251894", "2025 • Horror • TBD"),
            Movie(R.drawable.trainyourdragon, "How to Train Your Dragon", "On the rugged isle of Berk, a Viking boy named Hiccup defies centuries of tradition by befriending a dragon named Toothless.", "https://www.netflix.com/ae-en/title/80039394", "2025 • Fantasy/Action • 1h 56m"),
            Movie(R.drawable.untildown, "Until Dawn", "One year after her sister disappeared, Clover and her friends head to the remote valley where she vanished to search for answers.", "https://www.imdb.com/title/tt30955489/", "2025 • Horror/Survival • 1h 43m"),
        )

        allRegularMovies = listOf(
            Movie(R.drawable.damsel, "Damsel", "A young woman is sacrificed to a dragon but discovers the truth about her kingdom.", "https://www.netflix.com/ae-en/title/80991090", "2024 • Fantasy-Adventure • 1h47m"),
            Movie(R.drawable.us, "Us", "A family faces their terrifying doppelgängers during a vacation.", "https://www.netflix.com/ae-en/title/81026600", "2019 • Horror-Thriller • 1h56m"),
            Movie(R.drawable.intothewoods, "Into The Woods", "Fairy tale characters face consequences when their stories intertwine.", "https://www.netflix.com/ae-en/title/81108061", "2014 • Musical-Fantasy • 2h5m")
        )

        // Setup RecyclerViews
        val recyclerNew = findViewById<RecyclerView>(R.id.recyclerNew)
        val recyclerUpcoming = findViewById<RecyclerView>(R.id.recyclerUpcoming)
        val recyclerRegular = findViewById<RecyclerView>(R.id.recyclerRegular)

        recyclerNew.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerUpcoming.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerRegular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerNew.adapter = MovieAdapter(allNewMovies) { movie -> openMovieInfo(movie) }
        recyclerUpcoming.adapter = MovieAdapter(allUpcomingMovies) { movie -> openMovieInfo(movie) }
        recyclerRegular.adapter = MovieAdapter(allRegularMovies) { movie -> openMovieInfo(movie) }
    }

    private fun openMovieInfo(movie: Movie) {
        val intent = Intent(this, MovieDetailScreen::class.java).apply {
            putExtra("poster", movie.poster)
            putExtra("title", movie.title)
            putExtra("description", movie.description)
            putExtra("url", movie.url)
            putExtra("info", movie.movieInfo)
        }
        startActivity(intent)
    }

    private fun showInstructionPopup() {
        val dialogView = layoutInflater.inflate(R.layout.popup_instruction, null)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val closeButton = dialogView.findViewById<ImageButton>(R.id.closePopup) // FIXED THIS LINE
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
    }
}
