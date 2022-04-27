package com.milkcandy.cupofsugar

import android.util.Log

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth
    private  lateinit var db: FirebaseFirestore
     // init database
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        db= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
        db.collection("Users")
    }

    fun register(view: View) {
        val email = editTextEmailAddress.text.toString()
        val password = editTextPassword.text.toString()
        val name = editName.text.toString()
        val profileImageURL = "ProfilePictures/defaultProfilePic.jpg"

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                // Adding user to database
                    val user = auth.currentUser
                    val userString = user?.uid.toString()//gets new user
                    Log.d(TAG,"RAN")
                val userInfo = hashMapOf(
                    "email" to email,
                    "password" to password,
                    "name"     to name,
                    "profileImageURL"  to profileImageURL
                )

                db.collection("Users").document(userString).set(userInfo)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // If sign in fails, display a message to the user
                Toast.makeText(
                    baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun goToLogin(view: View){
        val intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

}