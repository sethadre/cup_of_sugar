package com.example.cupofsugar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory  //for decoding file
import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ItemPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_post)
        
        //Start of Back End Stuff
        val storage = Firebase.storage
        // Create a reference with an initial file path and name
        val imagePathReference = storage.reference.child("kirby.jpg")
        //This is how we download to memory and not as local file
        val ONE_MEGABYTE: Long = 1024 * 1024
        // WE LOAD AN IMAGE IN IMAGEVIEW HERE
            imagePathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                // Data for "images/kirby.jpg" is returned, use this as needed
                val bitmap = BitmapFactory.decodeByteArray(it,0,it.size) //it is like "this" .getBytes is a task byte array is parameter
                findViewById<ImageView>(R.id.postImg).setImageBitmap(bitmap)
        }.addOnFailureListener {
            //Make Toast incase image/post didn't exist
        }
        //End Back End Stuff


        val cancelButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.cancelActionButton)
        cancelButton.setOnClickListener {
            val intent = Intent(this, ItemsPageActivity::class.java)
            startActivity(intent)
            finish()
        }

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
    }

    fun goToPostLocation(view: View) {
        val intent = Intent(this, GoogleMapsActivity::class.java)
        startActivity(intent)
        finish()
    }
}