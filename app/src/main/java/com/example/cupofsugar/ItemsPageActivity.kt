package com.example.cupofsugar

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
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

        //HARDCODED MANUALLY GRABBED
        //TODO : Array? or something to autoload images based on tags or distance
        val postPath1   = storage.reference.child("postImages/post0/laptop.jpg")
        val postPath2   = storage.reference.child("postImages/post0/cake.jpg")
        val postPath3   = storage.reference.child("postImages/post0/candy.jpg")
        val postPath4   = storage.reference.child("postImages/post0/coffee.jpeg")
        val postPath5   = storage.reference.child("postImages/post0/monster.jpg")
        val postPath6   = storage.reference.child("postImages/post0/sugar.jpeg")
        val postPath7   = storage.reference.child("postImages/post0/sweater.jpeg")
        val postPath8   = storage.reference.child("postImages/post0/laptop.jpg")
        val postPath9   = storage.reference.child("postImages/post0/tomato.jpg")
        val postPath10  = storage.reference.child("postImages/post0/cake.jpg")
        val postPath11  = storage.reference.child("postImages/post0/sugar.jpeg")
        val postPath12  = storage.reference.child("postImages/post0/candy.jpg")
//
        //Also hard coded clicks
        //TODO: automate it
        val postClick1  = findViewById<ImageView>(R.id.postView1)
        val postClick2  = findViewById<ImageView>(R.id.postView2)
        val postClick3  = findViewById<ImageView>(R.id.postView3)
        val postClick4  = findViewById<ImageView>(R.id.postView4)
        val postClick5  = findViewById<ImageView>(R.id.postView5)
        val postClick6  = findViewById<ImageView>(R.id.postView6)
        val postClick7  = findViewById<ImageView>(R.id.postView7)
        val postClick8  = findViewById<ImageView>(R.id.postView8)
        val postClick9  = findViewById<ImageView>(R.id.postView9)
        val postClick10 = findViewById<ImageView>(R.id.postView10)
        val postClick11 = findViewById<ImageView>(R.id.postView11)
        val postClick12 = findViewById<ImageView>(R.id.postView12)
//

        postPath1.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val bitmap1 = BitmapFactory.decodeByteArray(it,0,it.size)
            findViewById<ImageView>(R.id.postView1).setImageBitmap(bitmap1)
        }
        postPath2.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val bitmap2 = BitmapFactory.decodeByteArray(it,0,it.size)
            findViewById<ImageView>(R.id.postView2).setImageBitmap(bitmap2)
        }
        postPath3.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val bitmap3 = BitmapFactory.decodeByteArray(it,0,it.size)
            findViewById<ImageView>(R.id.postView3).setImageBitmap(bitmap3)
        }

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


        //Firebase declare
        db= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
        val user = auth.currentUser
        val docRef = db.collection("Users").document(user?.uid.toString())
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
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

//        val postActionButton =
//            findViewById<Button>(R.id.goToPostButton)
//        postActionButton.setOnClickListener {
//            val intent = Intent(this, ItemsPageActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
        val createPostActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.createPostActionButton)
        createPostActionButton.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
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
        val createButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.createActionButton)
        createButton.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
            finish()
        }
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