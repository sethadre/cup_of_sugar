package com.milkcandy.cupofsugar

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_support.*
import android.widget.AutoCompleteTextView
import kotlinx.android.synthetic.main.fragment_topic.*


class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)
        supportActionBar?.hide()

        //add fragment
        if (findViewById<FrameLayout>(R.id.frameLayout) != null)
            supportFragmentManager.beginTransaction().add(R.id.frameLayout, TopicFragment()).commit()

        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {
            val s: String = autoCompleteTextView.text.toString()
            val i = Intent(Intent.ACTION_SEND)
            i.type = "message/rfc822"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf("cupofsugar.com@gmail.com"))
            i.putExtra(Intent.EXTRA_SUBJECT, s)
            i.putExtra(Intent.EXTRA_TEXT, messageText.text.toString())
            try {
                startActivity(Intent.createChooser(i, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "There are no email clients installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
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