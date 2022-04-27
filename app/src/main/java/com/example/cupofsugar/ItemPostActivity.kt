package com.milkcandy.cupofsugar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory  //for decoding file
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_homepage.*

class ItemPostActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_post)

        db = FirebaseFirestore.getInstance()//important to place inside this function

        //Start of Back End Stuff
        val storage = Firebase.storage
        // Create a reference with an initial file path and name
        //val imagePathReference = storage.reference.child("kirby.jpg")
        //This is how we download to memory and not as local file
        val ONE_MEGABYTE: Long = 1024 * 1024
        // WE LOAD AN IMAGE IN IMAGEVIEW HERE
//        imagePathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//            // Data for "images/kirby.jpg" is returned, use this as needed
//            val bitmap = BitmapFactory.decodeByteArray(
//                it,
//                0,
//                it.size
//            ) //it is like "this" .getBytes is a task byte array is parameter
//            findViewById<ImageView>(R.id.imageView1).setImageBitmap(bitmap)
//        }.addOnFailureListener {
//            //Make Toast incase image/post didn't exist
//        }
        //End Back End Stuff

        val titlePass = intent.getStringExtra("titleKey")
        val titleTextView: TextView = findViewById(R.id.title)
        titleTextView.text = "Title: $titlePass"
        val descriptionPass = intent.getStringExtra("descriptionKey")
        val descriptionTextView: TextView = findViewById(R.id.description)
        descriptionTextView.text = "Description: $descriptionPass"
        val ownerIDPass = intent.getStringExtra("ownerIDKey")
        Log.d("OWNER ID IN ITEMS PAGE", ownerIDPass.toString())
        val ownerRef = db.collection("Users").document(ownerIDPass.toString())
        ownerRef.get().addOnSuccessListener { document ->

            if (document != null) {
                Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                val userName: StringBuffer = StringBuffer()
                userName.append(document.data?.getValue("name"))
                val ownerTextView: TextView = findViewById(R.id.owner)
                ownerTextView.text = "Owner: $userName"
            } else {
                Log.d("TAG", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
        val statePass = intent.getStringExtra("stateKey")
        val cityPass = intent.getStringExtra("cityKey")
        val docIDPass = intent.getStringExtra("docIDKey")

        val itemRef =
            db.collection("Items").document(statePass.toString()).collection(cityPass.toString())
                .document(docIDPass.toString())
        itemRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("TAG", "DocumentSnapshot data: ${document.data}\n\n") //this gets the data
                val imgURLResult: StringBuffer = StringBuffer()

                val url0 = (document.data?.get("imageURLS") as List<String>)[0]

                val postPath0 = storage.reference.child(url0)

                postPath0.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                    val bitmap0 = BitmapFactory.decodeByteArray(it, 0, it.size)
                    findViewById<ImageView>(R.id.imageView0).setImageBitmap(bitmap0)
                }
//                val url1 = (document.data?.get("imageURLS") as List<String>)[1]
//
//                val postPath1 = storage.reference.child(url1)
//
//                postPath1.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//                    val bitmap1 = BitmapFactory.decodeByteArray(it, 0, it.size)
//                    findViewById<ImageView>(R.id.imageView1).setImageBitmap(bitmap1)
//                }
//                val url2 = (document.data?.get("imageURLS") as List<String>)[2]
//
//                val postPath2 = storage.reference.child(url2)
//
//                postPath2.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//                    val bitmap2 = BitmapFactory.decodeByteArray(it, 0, it.size)
//                    findViewById<ImageView>(R.id.imageView2).setImageBitmap(bitmap2)
//                }
            } else {
                Log.d("TAG", "No such document")
            } }


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
    }

    fun goToPostLocation(view: View) {
        val intent = Intent(this, GoogleMapsActivity::class.java)
        startActivity(intent)
        finish()
    }
}