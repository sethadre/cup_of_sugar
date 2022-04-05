package com.example.cupofsugar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

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
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Delete account option
        val deleteAccountButton =
            findViewById<Button>(R.id.deleteAccountButton)
        deleteAccountButton.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Delete account?")
                .setMessage("Your account and data will be deleted. Deleting your account cannot be undone. Are you sure?")
                .setNegativeButton("Cancel") { dialog, which ->
                    Snackbar.make(rootLayout, "Action Canceled", Snackbar.LENGTH_SHORT).show()
                }
                .setPositiveButton("Confirm Deletion") { dialog, which ->
                    auth = FirebaseAuth.getInstance()
                    db = FirebaseFirestore.getInstance()
                    val user = auth.currentUser
                    // delete from Authentification
                    user?.delete()?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // delete user's document Users collection
                            db.collection("Users").document(user.uid).delete()
                            // end user session
                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Snackbar.make(rootLayout, "Account deletion failed. Relog and try again.", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
                .show()
        }
    }
}
