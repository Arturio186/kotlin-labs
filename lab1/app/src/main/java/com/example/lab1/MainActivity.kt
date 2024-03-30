package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val inputElement = findViewById<EditText>(R.id.editTextText)
        val outputElement = findViewById<TextView>(R.id.textView)

        button.setOnClickListener {
            outputElement.setText(inputElement.text)
            inputElement.setText("")
        }
    }
}