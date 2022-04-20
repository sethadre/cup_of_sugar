package com.example.cupofsugar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.activity_settings.*

class UpdateEmailActivity : AppCompatActivity(){

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_email)
        supportActionBar?.hide()

        db = FirebaseFirestore.getInstance()//important to place inside this function


        //Start of Back End Stuff
        val storage = Firebase.storage

        val backActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.backActionButton)
        backActionButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val docRef = db.collection("Users").document(user?.uid.toString())
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d(ItemsPageActivity.TAG, "DocumentSnapshot data: ${document.data}") //this gets the data
                //Outputting users
                val result: StringBuffer = StringBuffer()
                result.append(document.data?.getValue("email")).append(" ")
                val editText = findViewById<EditText>(R.id.emailEditText)

                editText.setText(result)
            } else {
                Log.d(ItemsPageActivity.TAG, "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d(ItemsPageActivity.TAG, "get failed with ", exception)
            }





    }
}