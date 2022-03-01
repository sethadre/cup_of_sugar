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
import kotlinx.android.synthetic.main.activity_item_post.*
import kotlinx.android.synthetic.main.profile.*
import java.text.SimpleDateFormat
import java.util.*


class CreatePostActivity : AppCompatActivity() {
    //Firebase stuff
    private lateinit var auth: FirebaseAuth
    private  lateinit var db: FirebaseFirestore
    //val storage = Firebase.storage

    //private val GALLERY_REQUEST_CODE = 100
    private lateinit var imageUri : Uri //uri for uploading to firebase
    private lateinit var testImg1: ImageView //late init is so you can initialize later which loads xml
    private lateinit var testImg1Uri: Uri // uri for 1st image preview to be uploaded
    private lateinit var testImg2: ImageView
    private lateinit var testImg2Uri: Uri // 2nd
    private lateinit var testImg3: ImageView
    private lateinit var testImg3Uri: Uri // 3rd
    //url for new post image might need to change to String Array for multiple images
    private var postCount = 0
    private var uploadCount = 1 //used in openGallery and activity result

    companion object{
        const val TAG = "CreatePostActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        testImg1 = findViewById(R.id.previewImg1) //For use below for when photo is uploaded to preview here
        testImg2 = findViewById(R.id.previewImg2)
        testImg3 = findViewById(R.id.previewImg3)
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

        //FOR DELETE IMAGE PREVIEW
        //USE THIS LINE FOR EACH PREVIEW AND CHANGE COMMENT THE ONE BELOW
        //If (delete_preview_photo is pressed)
            //testImg1.setImageURI(null)//THIS WILL CLEAR PREVIEW AFTER UPLOAD

        // Filter dropdown menu
//        spinner_filters.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                adapterView: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                Toast.makeText(this@CreatePostActivity,
//                    adapterView?.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) { }
//        }
        // End filter dropdown menu

                                //CAMERA
    val buttonTakePhoto =
            findViewById<Button>(R.id.button_take_photo)
        buttonTakePhoto.setOnClickListener{
            //placeholder code to ask for camera privileges
        }

    }
     private fun openGallery() { //this function opens gallery and the photo you pick is displayed

         //upload count variable is global to actvity until activity canceled

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        if (uploadCount == 1) {
            startActivityForResult(intent, 1)
            uploadCount = 2
        }
        else if(uploadCount == 2){
            startActivityForResult(intent, 1)
            uploadCount =3
        }
        else if(uploadCount == 3){
            startActivityForResult(intent, 1)
            uploadCount = 1
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int,data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            imageUri = data?.data!! //passed to firebase below
            if(uploadCount == 1) {
                testImg1.setImageURI(data?.data) //each line for a photo that may be previewed
                testImg1Uri = data?.data!!
            }
            else if(uploadCount == 2) {
                testImg2.setImageURI(data?.data)
                testImg2Uri = data?.data!!
            }
            else if(uploadCount == 3) {
                testImg3.setImageURI(data?.data)
                testImg3Uri = data?.data!!
            }
             //needs option to delete photo preview
            //change photo size later
        }
    }
    //FireBase Post upload
    private fun uploadImage(): List<String> {
        // upload Image and get URL it returns
        //check if image previews 1,2,3 are null or not
        var image1URL =""
        var image2URL = ""
        var image3URL = ""

        var imageURLS: List<String> = listOf() //return this

        //val formatter = get USERID UI and add to formatter for folder
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName1 = formatter.format(now)+ "1"
        val fileName2 = formatter.format(now) + "2"
        val fileName3 = formatter.format(now) + "3"
        //GET USERID AND TITLE
        val user = auth.currentUser
        val docRef = db.collection("Users").document(user?.uid.toString())
        val userString = user?.uid.toString()//gets user
        //val ifNoDirectory = userString  //Each post's photos is a new directory based on UserID
        val newDirectory = "post:" + postCount.toString()
        //val storageReference = FirebaseStorage.getInstance().getReference("postImages/$ifNoDirectory/$newDirectory/$fileName1")
        //newImageURL = "postImages/$newDirectory/$fileName" //where to upload new post photo [delete this line]
        if (testImg1.getDrawable() != null) { //if image not empty UPLOAD IT
            val storageReference = FirebaseStorage.getInstance().getReference("postImages/$newDirectory/$fileName1")
            storageReference.putFile(testImg1Uri).addOnSuccessListener {
                image1URL = "postImages/$newDirectory/$fileName1" //where to upload new post photo
                //testImg1.setImageURI(null)//THIS WILL CLEAR PREVIEW AFTER UPLOAD
                Toast.makeText(this@CreatePostActivity, "Sucessful Upload", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(this@CreatePostActivity, "Failed Upload", Toast.LENGTH_SHORT).show()
            }
            image1URL = "postImages/$newDirectory/$fileName1" //where to upload new post photo
            imageURLS += (image1URL)
        }
        //TEST NULL HERE
        if (testImg2.getDrawable() != null) {
            val storageReference = FirebaseStorage.getInstance().getReference("postImages/$newDirectory/$fileName2")
            storageReference.putFile(testImg2Uri)//for other images
            image2URL = "postImages/$newDirectory/$fileName2" //where to upload new post photo
            imageURLS += (image2URL)
        }
        if (testImg3.getDrawable() != null) {
            val storageReference = FirebaseStorage.getInstance().getReference("postImages/$newDirectory/$fileName3")
            storageReference.putFile(testImg3Uri)//for other image
            image3URL = "postImages/$newDirectory/$fileName3" //where to upload new post photo
            imageURLS += (image3URL)
        }

        return imageURLS
    }
    fun submitPost(view: View){

        //Tasks
        //Instances
        db= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
        //get Image url


        //get title, description, category
        var title: EditText = findViewById(R.id.postTitle) //get title
        var titleString :String = title.text.toString()
        //get description
        var desc : EditText = findViewById(R.id.textDescriptionBox)
        var descString : String = desc.text.toString()//get desc

        var categoryString : String = "plants"

        //FOR RYAN
        val latitude = 0.0
        val longitude = 0.0

        var location: List<Double> = listOf(latitude,longitude)
        //var finalLocation = {location: new Firebase.Firestore.GeoPoint(latitude,longitude)}

        //AFTER GETTING GEOPOINT YOU PLACE IT HERE TO CONVERT TO A CITY

        val city = "Long Beach"
       //End of location stuff

        // upload Image and get URL it returns
        //check if image previews 1,2,3 are null or not
        var imageURLS: List<String> = uploadImage()
        //val rating = 5 //ITS FREE ITEM NO RATING give rating
        //get UserID, and that is a reference to a database location
        val user = auth.currentUser
        //val docRef = db.collection("Users").document(user?.uid.toString())
        val userString = user?.uid.toString()//gets user



        //Pull POST NUMBER AND INCREMENT IT FOR EACH NEW POST
        val postCountString = postCount.toString()
        postCount += 1


        val postInfo = hashMapOf(
            "title" to titleString,
            "description" to descString,
            "category"  to categoryString,
            "location" to location,
            "imageURLS" to imageURLS,
            "owner"      to userString
        )
        //Going to posts database
        db.collection("Items").document(city).collection("$postCountString").document(postCountString).set(postInfo)
            .addOnSuccessListener { Log.d(TAG, "Post succesfully submitted") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing post document to database", e) }
    }
}