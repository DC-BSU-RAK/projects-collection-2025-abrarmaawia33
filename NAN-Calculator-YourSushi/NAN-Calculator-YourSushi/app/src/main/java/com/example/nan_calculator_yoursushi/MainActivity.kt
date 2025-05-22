package com.example.nan_calculator_yoursushi

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var checkBoxes: List<CheckBox>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize checkboxes (update IDs to match your XML layout)
        checkBoxes = listOf(
            findViewById(R.id.checkbox_tuna),
            findViewById(R.id.checkbox_salmon),
            findViewById(R.id.checkbox_avocado),
            findViewById(R.id.checkbox_shrimp),
            findViewById(R.id.checkbox_cucumber),
            findViewById(R.id.checkbox_egg)
        )

        // Menu button popup
        val menuButton: ImageButton = findViewById(R.id.menuButton)
        menuButton.setOnClickListener {
            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.activity_instructionscreen, null)
            val instructWindow = PopupWindow(popupView, 800, 500, true)
            instructWindow.showAtLocation(popupView, Gravity.BOTTOM, 20, 100)

            val backButton: Button = popupView.findViewById(R.id.Backbutton)
            backButton.setOnClickListener {
                instructWindow.dismiss()
            }
        }

        // Done button
        val doneButton: Button = findViewById(R.id.donebutton)
        doneButton.setOnClickListener {
            val selected = checkBoxes.filter { it.isChecked }.map { it.text.toString() }

            if (selected.size != 3) {
                Toast.makeText(this, "Please select exactly 3 ingredients.", Toast.LENGTH_SHORT).show()
            } else {
                val (sushiName, sushiImageResId) = getSushiDishAndImage(selected)

                val dialogView = layoutInflater.inflate(R.layout.activity_resultscreen_popup, null)
                val resultView = dialogView.findViewById<TextView>(R.id.sushiResult)
                val imageView = dialogView.findViewById<ImageView>(R.id.sushiImage)
                val closeButton = dialogView.findViewById<Button>(R.id.closeButton)

                resultView.text = sushiName
                imageView.setImageResource(sushiImageResId)

                val dialog = AlertDialog.Builder(this)
                    .setView(dialogView)
                    .create()

                closeButton.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
    }

    private fun getSushiDishAndImage(ingredients: List<String>): Pair<String, Int> {
        val sorted = ingredients.sorted()

        return when (sorted) {
            listOf("Tuna", "Salmon", "Avocado") ->
                "Rainbow Roll" to R.drawable.rainbow_roll
            listOf("Shrimp", "Cucumber", "Avocado") ->
                "Dragon Roll" to R.drawable.dragon_roll
            listOf("Egg", "Cucumber", "Salmon") ->
                "Tamago Delight" to R.drawable.tamago_delight
            listOf("Tuna", "Egg", "Shrimp") ->
                "Ocean Special" to R.drawable.ocean_special
            listOf("Salmon", "Avocado", "Cucumber") ->
                "Sunset Maki" to R.drawable.sunset_maki
            else ->
                "Sushi Surprise" to R.drawable.random_sushi
        }
    }
}
