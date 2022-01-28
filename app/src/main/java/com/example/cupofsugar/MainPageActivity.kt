package com.example.cupofsugar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main_page.*

class MainPageActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    private  lateinit var db: FirebaseFirestore
    private val TAG = "MainPageActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        setContentView(R.layout.activity_main_page)
    }

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

    fun logout(view: View){
        val intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToHome(view: View){
        // already home
    }
    fun goToItems(view: View){
        val intent= Intent(this,ItemsPageActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToRegister(view: View){
        val intent= Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }
}