package com.example.cupofsugar

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.items_homepage.*


class ItemsPageActivity : AppCompatActivity() {

    //Firebase init
    private lateinit var auth: FirebaseAuth
    private  lateinit var db: FirebaseFirestore
    private val TAG = "ItemsPageActivity"

    //Grid
    val postint = 0
    private lateinit var gridView: GridView
    private var posts = arrayOf("Post0")
    private var postImages = intArrayOf(postint)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      //Front End for Grid
        //gridView = findViewById()



        //Start of Back End Stuff
        val storage = Firebase.storage
        //This is how we download to memory and not as local file
        val ONE_MEGABYTE: Long = 1024 * 1024

//        val iv1 = findViewById<ImageView>(R.id.postView1)
//        val iv2 = findViewById<ImageView>(R.id.postView2)
//        val iv3 = findViewById<ImageView>(R.id.postView3)

        //Image View array

        //val IMGS = arrayListOf<ImageView>(iv1, iv2, iv3)

        //IMGS += iv4
        //adds iv4 to IMGS array

        //TODO GRAB STUFF FROM DB


        //this pulls the email from the database

        //Firebase declare
        db= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
        val user = auth.currentUser
        val docRef = db.collection("Users").document(user?.uid.toString())
        //val itemRef = db.collection("Items").document("ufFoRA4axiYE5oQ7eLDnFqKl8bA3flowers 2")
       // val photoRef = db.collection("Items").document(itemRef.toString()).collection("postImages/post#2022_02_23_17_28_37/2022_02_23_17_28_37")


        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}") //this gets the data
                //Outputting users
                val result: StringBuffer = StringBuffer()
                result.append(document.data?.getValue("email")).append(" ")
                textViewResult.setText(result)
            } else {
                Log.d(TAG, "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        // end of firebase stuff

        setContentView(R.layout.items_homepage) //moved this line lower


        val createPostActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.createPostActionButton)
        createPostActionButton.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
            finish()
        }


        // BOTTOM BAR BUTTONS
        // ****************************************************************************************
        // ****************************************************************************************
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

        //We are home we dont need functionality

//        val HomeActionButton =
//            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.homeActionButton)
//        HomeActionButton.setOnClickListener {
//            val intent = Intent(this, ItemsPageActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

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
        // ****************************************************************************************
        // ****************************************************************************************
        // END BOTTOM BAR BUTTONS

    }

    //firebase stuff
    fun readFireStoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Users")
            .get()
            .addOnCompleteListener {

                val result: StringBuffer = StringBuffer()

                if(it.isSuccessful) {
                    for(document in it.result!!) {
                        result.append(document.data.getValue("email")).append(" ")
                    }
                    textViewResult.setText(result)
                }
            }

    }
    //end of firebase stuff



    fun logout(view: View){
        val intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()

    }
    fun goToPost(view: View) {
        val intent = Intent(this, ItemPostActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToHome(view: View) {
        // already home
    }

}

// ****************************                                         ***************************
// ****************************   OLD CODE KEEP FOR ADRIAN's REFERENCE  ***************************
// ****************************                                         ***************************

//HARDCODED MANUALLY GRABBED
//TODO : Array? or something to autoload images based on tags or distance
//        val postPath1   = storage.reference.child("postImages/post0/tomato.jpg")
//        val postPath2   = storage.reference.child("postImages/post0/cake.jpg")
//        val postPath3   = storage.reference.child("postImages/post0/candy.jpg")
//
//        postPath1.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//            val bitmap1 = BitmapFactory.decodeByteArray(it,0,it.size)
//            findViewById<ImageView>(R.id.postView1).setImageBitmap(bitmap1)
//        }
//        postPath2.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//            val bitmap2 = BitmapFactory.decodeByteArray(it,0,it.size)
//            findViewById<ImageView>(R.id.postView2).setImageBitmap(bitmap2)
//        }
//        postPath3.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//            val bitmap3 = BitmapFactory.decodeByteArray(it,0,it.size)
//            findViewById<ImageView>(R.id.postView3).setImageBitmap(bitmap3)
//        }


//Also hard coded clicks
//TODO: automate it

//        val postClick1  = findViewById<ImageView>(R.id.postView1)
//        val postClick2  = findViewById<ImageView>(R.id.postView2)
//        val postClick3  = findViewById<ImageView>(R.id.postView3)
//
//        postClick1.setOnClickListener {
//            Toast.makeText(this,"Image 1 Clicked",Toast.LENGTH_SHORT).show()
//        }
//        postClick2.setOnClickListener {
//            Toast.makeText(this,"Image 1 Clicked",Toast.LENGTH_SHORT).show()
//        }
//        postClick3.setOnClickListener {
//            Toast.makeText(this,"Image 1 Clicked",Toast.LENGTH_SHORT).show()
//        }



//        imageClick9.setOnClickListener {
//            //Right now theyre toast messages,
//            //Need to replace the toasts with codes to open the post info
//            Toast.makeText(this,"Image clicked, I lied", Toast.LENGTH_SHORT).show()
//        }
//        imageClick10.setOnClickListener {
//            Toast.makeText(this,"Image clicked, such Lollipop", Toast.LENGTH_SHORT).show()
//        }
//        imageClick11.setOnClickListener {
//            Toast.makeText(this,"Image clicked, MAXIMUM TOMATER", Toast.LENGTH_SHORT).show()
//        }
//        imageClick12.setOnClickListener {
//            Toast.makeText(this,"Image clicked, the cake is a lie", Toast.LENGTH_SHORT).show()
//        }
