package com.example.reservasiticketintentapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker

class MainActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var timerEditText: EditText
    private lateinit var calendarEditText: EditText
    private lateinit var destinationSpinner: Spinner
    private lateinit var reservationButton: Button

    private val destinations = arrayOf("Jakarta", "Bandung", "Surabaya", "Yogyakarta")
    private val prices = mapOf(
        "Jakarta" to 100000,
        "Bandung" to 80000,
        "Surabaya" to 110000,
        "Yogyakarta" to 90000
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.usernameEditText)
        timerEditText = findViewById(R.id.timerEditText)
        calendarEditText = findViewById(R.id.calenderEditText)
        destinationSpinner = findViewById(R.id.destinationSpinner)
        reservationButton = findViewById(R.id.reservtionButton)

        setupSpinner()
        setupCustomDatePicker()
        setupCustomTimePicker()

        reservationButton.setOnClickListener {
            showCustomDialog()
        }
    }

    private fun setupSpinner() {
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, destinations)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        destinationSpinner.adapter = spinnerAdapter
    }

    private fun setupCustomDatePicker() {
        calendarEditText.setOnClickListener {
            showCustomCalendarDialog()
        }
    }

    private fun setupCustomTimePicker() {
        timerEditText.setOnClickListener {
            showCustomTimeDialog()
        }
    }

    private fun showCustomCalendarDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_calender, null)
        val datePicker: DatePicker = dialogView.findViewById(R.id.datePicker)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val day = datePicker.dayOfMonth
                val month = datePicker.month + 1 // Month is 0-indexed
                val year = datePicker.year
                val date = "$day/$month/$year"
                calendarEditText.setText(date)
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    private fun showCustomTimeDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_timer, null)
        val timePicker: TimePicker = dialogView.findViewById(R.id.timePicker)
        timePicker.setIs24HourView(true)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val hour = timePicker.hour
                val minute = timePicker.minute
                val time = String.format("%02d:%02d", hour, minute)
                timerEditText.setText(time)
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    private fun showCustomDialog() {
        val selectedDestination = destinationSpinner.selectedItem.toString()
        val price = prices[selectedDestination] ?: 0

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirm, null)
        val tarifTextView: TextView = dialogView.findViewById(R.id.tarifTextView)
        val hargaTextView: TextView = dialogView.findViewById(R.id.hargaTextView)
        val btnCancel: Button = dialogView.findViewById(R.id.btn_cancel)
        val btnConfirm: Button = dialogView.findViewById(R.id.btn_confirm)

        tarifTextView.text = "Tarif Tiket untuk tujuan $selectedDestination"
        hargaTextView.text = "Rp $price"

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            goToSecondActivity(price)
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun goToSecondActivity(price: Int) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("USERNAME", usernameEditText.text.toString())
        intent.putExtra("TIME", timerEditText.text.toString())
        intent.putExtra("DATE", calendarEditText.text.toString())
        intent.putExtra("DESTINATION", destinationSpinner.selectedItem.toString())
        intent.putExtra("PRICE", price)
        startActivity(intent)
    }
}
