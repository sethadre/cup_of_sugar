package com.example.cupofsugar

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.items_homepage.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.profile.*
//import com.google.firebase.storage.ktx.storage
import java.io.File


class CreatePostActivity : AppCompatActivity() {
    //Firebase stuff
    private lateinit var auth: FirebaseAuth
    private  lateinit var db: FirebaseFirestore
    //private lateinit var imageURI = Uri
    private val TAG = "CreatePostActivity"
//    val storage = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val cancelActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.cancelActionButton)
        cancelActionButton.setOnClickListener {
            val intent = Intent(this, ItemsPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        val buttonUploadPhoto =
            findViewById<Button>(R.id.button_upload_photo)
        buttonUploadPhoto.setOnClickListener{
            openGallery()
        }

//        val buttonTakePhoto =
//            findViewById<Button>(R.id.button_take_photo)
//        buttonTakePhoto.setOnClickListener{
//            //placeholder code to ask for camera privileges
//        }

    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            1
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1){
            imageView.setImageURI(data?.data) // handle chosen image
        }
    }

}