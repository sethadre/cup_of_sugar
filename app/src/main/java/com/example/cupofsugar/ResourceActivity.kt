package com.example.cupofsugar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.widget.TextView

class ResourceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource)

        val hlTextView = findViewById<TextView>(R.id.hotlineLink)
        hlTextView.movementMethod = LinkMovementMethod.getInstance()

        val govTextView = findViewById<TextView>(R.id.governmentLink)
        govTextView.movementMethod = LinkMovementMethod.getInstance()

        val foodTextView = findViewById<TextView>(R.id.foodLink)
        foodTextView.movementMethod = LinkMovementMethod.getInstance()

        val thriftTextView = findViewById<TextView>(R.id.thriftLink)
        thriftTextView.movementMethod = LinkMovementMethod.getInstance()

        val profileActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.profileActionButton)
        profileActionButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        val forumsActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.forumsActionButton)
        forumsActionButton.setOnClickListener {
            val intent = Intent(this, ForumPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        val HomeActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.homeActionButton)
        HomeActionButton.setOnClickListener {
            val intent = Intent(this, ItemsPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        val helpActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.helpActionButton)
        helpActionButton.setOnClickListener {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
            finish()
        }

        val settingsActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.settingsActionButton)
        settingsActionButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}