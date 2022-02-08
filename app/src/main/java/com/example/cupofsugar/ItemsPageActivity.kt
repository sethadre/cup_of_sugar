package com.example.cupofsugar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.items_homepage.*

class ItemsPageActivity : AppCompatActivity() {

    //Firebase stuff
    private lateinit var auth: FirebaseAuth
    private  lateinit var db: FirebaseFirestore
    private val TAG = "ItemsPageActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //Firebase stuff
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

        val postActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.postActionButton)
        profileActionButton.setOnClickListener {
            val intent = Intent(this, ItemPostActivity::class.java)
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
    fun goToHome(view: View) {
        // already home
    }
}