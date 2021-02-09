package com.example.mytrashyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ProcessActivity : AppCompatActivity() {
    var ms:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process)
        update()

    }

    fun buttonClick(view: View) {
        val textView: TextView = findViewById(R.id.textView)
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("ms", textView.text)
        }
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtra("ms", ms)
        })
    }

    fun update(){
        val textView: TextView = findViewById(R.id.textView)
        textView.text = "<<" + intent.getStringExtra("ms").toString() + ">>"
        ms = textView.text.toString()
    }

    override fun onResume() {
        super.onResume()
        update()
    }
}