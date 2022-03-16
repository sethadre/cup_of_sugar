package com.example.cupofsugar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SearchResultsActivity: AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val TAG= "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        title="Search Results"
        db = FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()

        val cancelActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.goBackButton)
        cancelActionButton.setOnClickListener {
            val intent = Intent(this, ItemsPageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}