package com.example.ei10m5Divisas

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var etAmount: EditText
    private lateinit var tvResult: TextView
    private lateinit var btnConvert: Button
    private lateinit var btnReset: Button

    private val currencies = arrayOf("USD", "EUR", "GBP", "JPY", "CLP")

    // las    tasas de cambio  (offline)
    private val exchangeRates = mapOf(
        "USD" to mapOf("USD" to 1.0, "EUR" to 0.92, "GBP" to 0.81, "JPY" to 134.0, "CLP" to 858.0),
        "EUR" to mapOf("USD" to 1.09, "EUR" to 1.0, "GBP" to 0.88, "JPY" to 145.65, "CLP" to 930.0),
        "GBP" to mapOf("USD" to 1.35, "EUR" to 1.14, "GBP" to 1.0, "JPY" to 165.0, "CLP" to 1150.0),
        "JPY" to mapOf("USD" to 0.0074, "EUR" to 0.0069, "GBP" to 0.0061, "JPY" to 1.0, "CLP" to 6.98),
        "CLP" to mapOf("USD" to 0.0012, "EUR" to 0.0011, "GBP" to 0.00087, "JPY" to 0.14, "CLP" to 1.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // hago el init   de las vistas
        spinnerFrom = findViewById(R.id.spinner_from)
        spinnerTo = findViewById(R.id.spinner_to)
        etAmount = findViewById(R.id.et_amount)
        tvResult = findViewById(R.id.tv_result)
        btnConvert = findViewById(R.id.btn_convert)
        btnReset = findViewById(R.id.btn_reset)

        // creo     el ArrayAdapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // le coloco  el adaptador a los Spinners
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        // pongo una  posición predeterminada para spinnerFrom en "CLP"
        val defaultPosition = adapter.getPosition("CLP")
        spinnerFrom.setSelection(defaultPosition)

        // la accion  de los botones
        btnConvert.setOnClickListener {
            convertCurrency()
        }

        btnReset.setOnClickListener {
            resetFields()
        }
    }

    private fun convertCurrency() {
        val fromCurrency = spinnerFrom.selectedItem.toString()
        val toCurrency = spinnerTo.selectedItem.toString()
        val amount = etAmount.text.toString().toDoubleOrNull()

        if (amount != null) {
            val rate = exchangeRates[fromCurrency]?.get(toCurrency) ?: 0.0
            val result = amount * rate
            tvResult.text = "Resultado: %.2f $toCurrency".format(result)
        } else {
            tvResult.text = "Por favor ingrese un monto válido."
        }
    }

    private fun resetFields() {
        etAmount.text.clear()
        spinnerFrom.setSelection(0)
        spinnerTo.setSelection(0)
        tvResult.text = "Resultado: 0"
    }
}
