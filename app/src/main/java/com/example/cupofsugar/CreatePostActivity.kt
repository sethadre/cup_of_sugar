package com.example.cupofsugar

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.net.Uri
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.items_homepage.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.android.synthetic.main.profile.*
import java.text.SimpleDateFormat
import java.util.*


class CreatePostActivity : AppCompatActivity() {
    //Firebase stuff
    private lateinit var auth: FirebaseAuth
    private  lateinit var db: FirebaseFirestore
    val storage = Firebase.storage

    private val GALLERY_REQUEST_CODE = 100
    private lateinit var imageUri : Uri //uri for uploading to firebase
    private lateinit var testImg: ImageView //late init is so you can initialize later which loads xml

    //url for new post image might need to change to String Array for multiple images
    private var newImageURL = ""

    companion object{
        const val TAG = "CreatePostActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        testImg = findViewById(R.id.previewImg) //For use below for when photo is uploaded to preview here

        val cancelActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.cancelActionButton)
        cancelActionButton.setOnClickListener {
            val intent = Intent(this, ItemsPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        val buttonUploadPhoto =
            findViewById<Button>(R.id.button_upload_photo)
        buttonUploadPhoto.setOnClickListener{
            openGallery()
        }

        // Filter dropdown menu
        spinner_filters.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CreatePostActivity,
                    adapterView?.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
        // End filter dropdown menu

                                //CAMERA
    val buttonTakePhoto =
            findViewById<Button>(R.id.button_take_photo)
        buttonTakePhoto.setOnClickListener{
            //placeholder code to ask for camera privileges
        }

    }
     private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        startActivityForResult(intent, 1)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int,data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            imageUri = data?.data!! //passed to firebase below
            testImg.setImageURI(data?.data) //each line for a photo that may be previewed
            //needs option to delete photo preview
            //change photo size later
        }
    }
    //FireBase Post upload
    private fun uploadImage(){
        //val formatter = get USERID UI and add to formatter for folder
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        //GET USERID AND TITLE
        val newDirectory = "post#"+fileName //Each post's photos is a new directory
        val storageReference = FirebaseStorage.getInstance().getReference("postImages/$fileName")
        newImageURL = "postImages/$newDirectory/$fileName" //where to upload new post photo
        storageReference.putFile(imageUri).addOnSuccessListener {
            testImg.setImageURI(null)//this will clear preview after upload
            Toast.makeText(this@CreatePostActivity,"Sucessful Upload",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this@CreatePostActivity,"Failed Upload",Toast.LENGTH_SHORT).show()
        }
    }
    fun submitPost(view: View){

        //Tasks
        //Instances
        db= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
        //get Image url

        // upload Image
        uploadImage()
        //get title
        var title: EditText = findViewById(R.id.postTitle) //get title
        //get description
        var desc : EditText = findViewById(R.id.textDescriptionBox) //get desc
        //val rating = 5 //ITS FREE ITEM NO RATING give rating
        //get UserID, and that is a reference to a database location
        //get Location
        //0 N 0 W for now

        //Going to posts database
        db.collection("Items").document(userString).set(userInfo)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
}