package com.ted.a7minutesworkout

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fLStartButton: FrameLayout = findViewById(R.id.flStart)
        fLStartButton.setOnClickListener {
            Toast.makeText(
                this@MainActivity,
                "Here we will start the exercise.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}