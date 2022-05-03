package com.milkcandy.cupofsugar

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.widget.TextView

class ResourceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource)
        supportActionBar?.hide()

        val hotlineLink =
            findViewById<android.widget.Button>(R.id.hotlineLink)
        hotlineLink.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apa.org/topics/crisis-hotlines"))
            startActivity(i)
        }

        val governmentLink =
            findViewById<android.widget.Button>(R.id.governmentLink)
        governmentLink.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.usa.gov/benefits"))
            startActivity(i)
        }

        val foodLink =
            findViewById<android.widget.Button>(R.id.foodLink)
        foodLink.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.foodpantries.org/"))
            startActivity(i)
        }

        val thriftLink =
            findViewById<android.widget.Button>(R.id.thriftLink)
        thriftLink.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.thethriftshopper.com/"))
            startActivity(i)
        }

        val profileActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.profileActionButton)
        profileActionButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        val chatActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.chatActionButton)
        chatActionButton.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
            finish()
        }

        val homeActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.homeActionButton)
        homeActionButton.setOnClickListener {
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