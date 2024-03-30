package com.example.lab2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val submit = findViewById<Button>(R.id.button2)

        val aInput = findViewById<EditText>(R.id.editTextText4)
        val bInput = findViewById<EditText>(R.id.editTextText5)
        val cInput = findViewById<EditText>(R.id.editTextText6)

        val output = findViewById<TextView>(R.id.textView2)

        submit.setOnClickListener {
            if (isFieldsEmpty(aInput, bInput, cInput)) {
                output.setText("Введите все поля!")

                return@setOnClickListener
            }

            if (!isFieldsNumbers(aInput, bInput, cInput)) {
                output.setText("Невалидные данные")

                return@setOnClickListener
            }

            val bValue = parseValue(bInput)?.toFloat() ?: 0f
            val aValue = parseValue(aInput)?.toFloat() ?: 0f
            val cValue = parseValue(cInput)?.toFloat() ?: 0f

            if (aValue == 0f) {
                if (bValue != 0f) {
                    val x = -1 * (cValue / bValue)

                    val roundedX = String.format("%.2f", x).toDouble()

                    output.setText("Линейное уравнение\nX = $roundedX")
                } else {
                    if (cValue != 0f) {
                        output.setText("Линейное уравнение\nКорней нет")
                    }
                    else {
                        output.setText("Линейное уравнение\nБескнечное множество решений")
                    }
                }
            }
            else {
                val D = bValue * bValue - 4 * aValue * cValue

                if (D > 0) {
                    val x1 = (-bValue + Math.sqrt(D.toDouble())) / (2 * aValue)
                    val x2 = (-bValue - Math.sqrt(D.toDouble())) / (2 * aValue)

                    val roundedX1 = String.format("%.2f", x1).toDouble()
                    val roundedX2 = String.format("%.2f", x2).toDouble()

                    output.setText("Квадратное уравнение\nx1 = $roundedX1\n x2 = $roundedX2")
                } else if (D == 0f) {
                    val x = -bValue / (2 * aValue)

                    val roundedX = String.format("%.2f", x).toDouble()

                    output.setText("Квадратное уравнение\nx1 = x2 = $roundedX")
                } else {
                    output.setText("Квадратное уравнение.\nДействительных корней нет")
                }
            }
        }
    }

    private fun isFieldsEmpty(vararg fields: EditText): Boolean {
        for (editText in fields) {
            if (editText.text.toString().isEmpty()) {
                return true
            }
        }
        return false
    }

    private fun isFieldsNumbers(vararg editTexts: EditText): Boolean {
        for (editText in editTexts) {
            val inputText = editText.text.toString().trim()

            if (inputText.isEmpty()) {
                return false
            }

            try {
                inputText.toInt()
            } catch (eInt: NumberFormatException) {
                try {
                    inputText.toFloat()
                } catch (eFloat: NumberFormatException) {
                    return false
                }
            }
        }
        return true
    }

    fun parseValue(editText: EditText): Number? {
        val inputText = editText.text.toString().trim()

        return try {
            inputText.toInt()
        } catch (eInt: NumberFormatException) {
            try {
                inputText.toFloat()
            } catch (eFloat: NumberFormatException) {
                null
            }
        }
    }
}