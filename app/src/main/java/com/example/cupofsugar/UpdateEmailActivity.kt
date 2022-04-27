package com.milkcandy.cupofsugar

//wtf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_update_email.*

class UpdateEmailActivity : AppCompatActivity() {

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
        val clearTextActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.clear_text)
        clearTextActionButton.setOnClickListener {
            val emailTextBox = findViewById<EditText>(R.id.emailEditText)
            emailTextBox.setText("")
        }

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val docRef = db.collection("Users").document(user?.uid.toString())
//        val emailRef = docRef.collection("email")
//        Log.d("EmailRef",emailRef.toString())
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d(
                    "in update email",
                    "DocumentSnapshot data in update email: ${document.data}"
                ) //this gets the data
                //Outputting users
                val result: StringBuffer = StringBuffer()
                result.append(document.data?.getValue("email"))
                val editText = findViewById<EditText>(R.id.emailEditText)

                editText.setText(result)
            } else {
                Log.d(ItemsPageActivity.TAG, "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d(ItemsPageActivity.TAG, "get failed with ", exception)
            }

        val updateEmailButton =
            findViewById<Button>(R.id.updateButton)
        updateEmailButton.setOnClickListener {
            val out = emailEditText.getText().toString()
            Log.d("Pressed update button", out)
            docRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    val result: StringBuffer = StringBuffer()
                    val currentEmail = result.append(document.data?.getValue("email")).toString()
                    Log.d("Pressed update button", currentEmail)
                    if (out == currentEmail) {
                        val text = "Email is the same"
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()

                    } else {

                        docRef.update("email", out)
                        docRef.get().addOnSuccessListener {
                            docRef.get().addOnSuccessListener { document ->
                                if (document != null) {
                                    val result: StringBuffer = StringBuffer()
                                    val newEmail =
                                        result.append(document.data?.getValue("email")).toString()
                                    Log.d("Email updated", newEmail)
                                    val text = "Email Updated please authenticate by following link in email"
                                    val duration = Toast.LENGTH_LONG
                                    val toast2 = Toast.makeText(applicationContext, text, duration)
                                    toast2.show()
                                }

                            }
                        }
                    }


                }//updateButtonEND

            }
        }
    }
}





