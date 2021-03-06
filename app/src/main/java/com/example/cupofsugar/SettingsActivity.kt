package com.milkcandy.cupofsugar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
        supportActionBar?.hide()

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

//        val settingsActionButton =
//            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.settingsActionButton)
//        settingsActionButton.setOnClickListener {
//            val intent = Intent(this, SettingsActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        // Delete user account
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
                    // Delete from Authentification
                    user?.delete()?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Delete user's document Users collection
                            db.collection("Users").document(user.uid).delete()
                            // End user session
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Snackbar.make(rootLayout, "Account deletion failed. Relog and try again.", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
                .show()
        }

        val updateEmailButton =
            findViewById<Button>(R.id.updateEmailButton)
        updateEmailButton.setOnClickListener{
            val intent = Intent (this,UpdateEmailActivity::class.java)
            startActivity(intent)
            finish()
        }

//        val updateUsernameButton =
//            findViewById<Button>(R.id.updateEmailButton)
//        updateUsernameButton.setOnClickListener{
//            val intent = Intent (this,UpdateUsernameActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        // Logout
        val logoutButton =
            findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        // Security (EDIT TO TAKE TO NEW PAGE FOR EDITING USER DETAILS)
//        val securityButton =
//            findViewById<Button>(R.id.securityButton)
//        securityButton.setOnClickListener {
//            val intent = Intent(this, SettingsActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
}
