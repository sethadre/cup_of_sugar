package com.example.cupofsugar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.items_homepage.*

class CreatePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val cancelActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.cancelActionButton)
        profileActionButton.setOnClickListener {
            val intent = Intent(this, ItemPostActivity::class.java)
            startActivity(intent)
            finish()
        }

        val buttonTakePhoto =
            findViewById<Button>(R.id.button_take_photo)
        buttonTakePhoto.setOnClickListener{
            //placeholder code to ask for camera privileges
        }

        val buttonUploadPhotos =
            findViewById<Button>(R.id.button_upload_photo)
        buttonUploadPhotos.setOnClickListener{
            //ask for photo
        }

    }
}