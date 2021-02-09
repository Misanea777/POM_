package com.example.mytrashyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonClick(view: View) {
        val intent = Intent(this, ProcessActivity::class.java).apply {
            putExtra("ms", findViewById<EditText>(R.id.input).text.toString())
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        findViewById<EditText>(R.id.input).setText(intent.getStringExtra("ms"))
    }



}