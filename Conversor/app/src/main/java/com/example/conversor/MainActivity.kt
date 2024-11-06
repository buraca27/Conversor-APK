package com.example.conversor

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var inputField: EditText
    private lateinit var outputField: TextView
    private lateinit var inputBaseSpinner: Spinner
    private lateinit var outputBaseSpinner: Spinner
    private lateinit var convertButton: Button

    private var inputBase = BaseType.DECIMAL
    private var outputBase = BaseType.BINARY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputField = findViewById(R.id.inputField)
        outputField = findViewById(R.id.outputField)
        inputBaseSpinner = findViewById(R.id.inputBaseSpinner)
        outputBaseSpinner = findViewById(R.id.outputBaseSpinner)
        convertButton = findViewById(R.id.convertButton)

        setupSpinners()

        convertButton.setOnClickListener {
            val inputValue = inputField.text.toString().trim()

            if (inputValue.isEmpty()) {
                outputField.text = "Por favor, insira um valor a ser convertido."
                outputField.visibility = View.GONE
                return@setOnClickListener
            }

            val outputValue = convertValue(inputValue, inputBase, outputBase)
            outputField.text = outputValue
            outputField.visibility = View.VISIBLE
        }
    }

    private fun setupSpinners() {
        val bases = BaseType.values().map { it.label }

        val inputAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bases)
        inputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputBaseSpinner.adapter = inputAdapter

        inputBaseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                inputBase = BaseType.values()[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val outputAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bases)
        outputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        outputBaseSpinner.adapter = outputAdapter

        outputBaseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                outputBase = BaseType.values()[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
