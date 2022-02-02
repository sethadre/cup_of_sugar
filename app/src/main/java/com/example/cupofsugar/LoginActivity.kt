package com.example.cupofsugar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val TAG= "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title="Login"
        db = FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
    }

    fun login(view: View){
        val email=editTextEmailAddress.text.toString()
        val password=editTextPassword.text.toString()
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                val intent= Intent(this,ItemsPageActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                // If sign in fails, display a message to the user.

                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

  /*  fun goToItems(view: View){
        val intent= Intent(this,ItemsPageActivity::class.java)
        startActivity(intent)
        finish()
    }*/
    fun goToForum(view: View){
        val intent= Intent(this,ForumPageActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToProfile(view: View){
        val intent= Intent(this,ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }
}