package com.milkcandy.cupofsugar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

// a future splash screen
class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)


        auth = FirebaseAuth.getInstance()

        Thread.sleep(3_000)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}