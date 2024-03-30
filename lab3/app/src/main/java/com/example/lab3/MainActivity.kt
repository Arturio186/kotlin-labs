package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val output = findViewById<TextView>(R.id.textViewNumber)
        var operation = ""
        var leftValue = ""
        var canAddDecimalPoint = true
        var canEqualOperation = false

        val digitButtons = listOf<Button>(
            findViewById<Button>(R.id.btn_one),
            findViewById<Button>(R.id.btn_two),
            findViewById<Button>(R.id.btn_three),
            findViewById<Button>(R.id.btn_four),
            findViewById<Button>(R.id.btn_five),
            findViewById<Button>(R.id.btn_six),
            findViewById<Button>(R.id.btn_seven),
            findViewById<Button>(R.id.btn_eight),
            findViewById<Button>(R.id.btn_nine)
        )

        val digitClickListener = View.OnClickListener { view ->
            if (leftValue != "" && !canEqualOperation) {
                canEqualOperation = true
                output.text = ""
            }

            val digit = (view as Button).text.toString()

            if (output.text.toString() == "0" || output.text.toString() == "") {
                output.text = digit
            }
            else {
                output.append(digit)
            }
        }

        digitButtons.forEach { button: Button ->
            button.setOnClickListener(digitClickListener)
        }

        val zeroButton = findViewById<Button>(R.id.btn_zero)

        zeroButton.setOnClickListener {
            if (leftValue != "" && !canEqualOperation) {
                canEqualOperation = true
                output.text = ""
            }

            if (output.text.toString() == "0") {
                return@setOnClickListener
            }
            output.append("0")
        }

        val clearButton = findViewById<Button>(R.id.btn_clear)

        clearButton.setOnClickListener {
            output.text = "0"
            canAddDecimalPoint = true
            leftValue = ""
            operation = ""
        }

        val plusMinusButton = findViewById<Button>(R.id.btn_plusMinus)

        plusMinusButton.setOnClickListener {
            var outputNum = output.text.toString().replace(',', '.').toDouble()

            outputNum *= -1

            val outputIntNum = convertToIntegerIfPossible(outputNum)

            if (outputIntNum != null) {
                output.text = outputIntNum.toString()
            }
            else {
                output.text = outputNum.toString().replace('.', ',')
            }
        }

        val dotButton = findViewById<Button>(R.id.btn_dot)

        dotButton.setOnClickListener {
            if (canAddDecimalPoint) {
                output.append(",")
                canAddDecimalPoint = false
            }
        }

        val operationButtons = listOf<Button>(
            findViewById<Button>(R.id.btn_division),
            findViewById<Button>(R.id.btn_multiply),
            findViewById<Button>(R.id.btn_minus),
            findViewById<Button>(R.id.btn_plus),
        )

        val operationClickListener = View.OnClickListener { view ->
            if (canEqualOperation) {
                val doubleRightValue = output.text.toString().replace(',', '.').toDouble()
                val doubleLeftValue = leftValue.replace(',', '.').toDouble()

                val doubleOutput = when (operation) {
                    "/" -> doubleLeftValue / doubleRightValue
                    "*" -> doubleLeftValue * doubleRightValue
                    "-" -> doubleLeftValue - doubleRightValue
                    "+" -> doubleLeftValue + doubleRightValue
                    else -> 0.0
                }

                val intOutput = convertToIntegerIfPossible(doubleOutput)

                if (intOutput != null) {
                    output.text = intOutput.toString()
                }
                else {
                    output.text = doubleOutput.toString().replace('.', ',')
                }

                canEqualOperation = false
            }

            leftValue = output.text.toString()

            when (view.id) {
                R.id.btn_division -> {
                    operation = "/"
                }
                R.id.btn_multiply -> {
                    operation = "*"
                }
                R.id.btn_minus -> {
                    operation = "-"
                }
                R.id.btn_plus -> {
                    operation = "+"
                }
            }
        }

        operationButtons.forEach { button: Button ->
            button.setOnClickListener(operationClickListener)
        }

        val equalButton = findViewById<Button>(R.id.btn_equal)

        equalButton.setOnClickListener {
            if (canEqualOperation) {
                val doubleRightValue = output.text.toString().replace(',', '.').toDouble()
                val doubleLeftValue = leftValue.replace(',', '.').toDouble()

                val doubleOutput = when (operation) {
                    "/" -> doubleLeftValue / doubleRightValue
                    "*" -> doubleLeftValue * doubleRightValue
                    "-" -> doubleLeftValue - doubleRightValue
                    "+" -> doubleLeftValue + doubleRightValue
                    else -> 0.0
                }

                val intOutput = convertToIntegerIfPossible(doubleOutput)

                if (intOutput != null) {
                    output.text = intOutput.toString()
                }
                else {
                    output.text = doubleOutput.toString().replace('.', ',')
                }

                leftValue = ""
                operation = ""
                canEqualOperation = false
            }
            else {
                leftValue = ""
                operation = ""
            }
        }
    }

    private fun convertToIntegerIfPossible(value: Double): Int? {
        val fractionalPart = value % 1
        return if (fractionalPart == 0.0) {
            value.toInt()
        } else {
            null
        }
    }
}