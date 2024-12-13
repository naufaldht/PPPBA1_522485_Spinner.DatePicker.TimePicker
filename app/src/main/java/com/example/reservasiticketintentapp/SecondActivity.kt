package com.example.reservasiticketintentapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker

class SecondActivity : AppCompatActivity() {

    private lateinit var namaTextView: TextView
    private lateinit var jamTextView: TextView
    private lateinit var tanggalTextView: TextView
    private lateinit var tujuanTextView: TextView
    private lateinit var hargaTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        namaTextView = findViewById(R.id.namaTextView)
        jamTextView = findViewById(R.id.jamTextView)
        tanggalTextView = findViewById(R.id.tanggalTextView)
        tujuanTextView = findViewById(R.id.tujuanTextView)
        hargaTextView = findViewById(R.id.hargaTextView)

        val username = intent.getStringExtra("USERNAME")
        val time = intent.getStringExtra("TIME")
        val date = intent.getStringExtra("DATE")
        val destination = intent.getStringExtra("DESTINATION")
        val price = intent.getIntExtra("PRICE", 0)

        namaTextView.text = username
        jamTextView.text = time
        tanggalTextView.text = date
        tujuanTextView.text = destination
        hargaTextView.text = "Rp $price"
    }
}
