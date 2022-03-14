package com.example.cupofsugar

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ProfileActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth//to get current user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Start of Back End Stuff
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val userString = user?.uid.toString()
        db = FirebaseFirestore.getInstance()
        val storage = Firebase.storage
        // Create a reference with an initial file path and name
        val imagePathReference = storage.reference.child("ProfilePictures/defaultProfilePic.jpg") //change to user's ProfileImageURL, its in their account
        //This is how we download to memory and not as local file
        val ONE_MEGABYTE: Long = 1024 * 1024

        //Here we get the Owner of the Post and receive it here to pass to the profile of that person.
        val ownerIDPass = intent.getStringExtra("ownerIDKey")
        val ownerRef = db.collection("Users").document(ownerIDPass.toString())
        ownerRef.get().addOnSuccessListener { document ->

            if (document != null) {
                Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                val userName: StringBuffer = StringBuffer()
                userName.append(document.data?.getValue("name"))
                val ownerTextView: TextView = findViewById(R.id.ownerText)
                ownerTextView.text = "Owner: $userName"
            } else {
                Log.d("TAG", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
        // WE LOAD AN IMAGE IN IMAGEVIEW HERE
        imagePathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            // Data for "images/kirby.jpg" is returned, use this as needed
            //it is like "this" .getBytes is a task byte array is parameter
            val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
            findViewById<ImageView>(R.id.profileImageView).setImageBitmap(bitmap)
        }.addOnFailureListener {
            //Make Toast incase image/post didn't exist
        }
        //End Back End Stuff

        val imageClick9 = findViewById<ImageView>(R.id.imageView9)
        val imageClick10 = findViewById<ImageView>(R.id.imageView10)
        val imageClick11 = findViewById<ImageView>(R.id.imageView11)
        val imageClick12 = findViewById<ImageView>(R.id.imageView12)


        val postImagePathReference9 = storage.reference.child("postImages/post0/sugar.jpeg")
        postImagePathReference9.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val bitmap1 = BitmapFactory.decodeByteArray(it,0,it.size)
            findViewById<ImageView>(R.id.imageView9).setImageBitmap(bitmap1)

        }

        val postImagePathReference10 = storage.reference.child("postImages/post0/laptop.jpg")
        postImagePathReference10.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val bitmap2 = BitmapFactory.decodeByteArray(it,0,it.size)
            findViewById<ImageView>(R.id.imageView10).setImageBitmap(bitmap2)
        }

        val postImagePathReference11 = storage.reference.child("postImages/post0/coffee.jpeg")
        postImagePathReference11.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val bitmap3 = BitmapFactory.decodeByteArray(it,0,it.size)
            findViewById<ImageView>(R.id.imageView11).setImageBitmap(bitmap3)
        }

        val postImagePathReference12 = storage.reference.child("postImages/post0/sweater.jpeg")
        postImagePathReference12.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val bitmap4 = BitmapFactory.decodeByteArray(it,0,it.size)
            findViewById<ImageView>(R.id.imageView12).setImageBitmap(bitmap4)
        }


        imageClick9.setOnClickListener {
            //Right now theyre toast messages,
            //Need to replace the toasts with codes to open the post info
            Toast.makeText(this,"Image clicked, I lied",Toast.LENGTH_SHORT).show()
        }
        imageClick10.setOnClickListener {
            Toast.makeText(this,"Image clicked, such Lollipop",Toast.LENGTH_SHORT).show()
        }
        imageClick11.setOnClickListener {
            Toast.makeText(this,"Image clicked, MAXIMUM TOMATER",Toast.LENGTH_SHORT).show()
        }
        imageClick12.setOnClickListener {
            Toast.makeText(this,"Image clicked, the cake is a lie",Toast.LENGTH_SHORT).show()
        }

        val messageActionButton =
            findViewById<android.widget.Button>(R.id.button3)
            messageActionButton.setOnClickListener{
                //user String and Owner user
                //You will load conversation Page Activity here
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