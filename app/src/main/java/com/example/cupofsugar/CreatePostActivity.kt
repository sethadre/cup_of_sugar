package com.example.cupofsugar

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentProviderClient
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.location.Geocoder
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

import android.location.Location
import android.location.LocationManager
import android.os.Handler
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import org.json.JSONException
import org.json.JSONObject

class CreatePostActivity : AppCompatActivity() {
    //Firebase stuff
    private lateinit var auth: FirebaseAuth
    private  lateinit var db: FirebaseFirestore
    //val storage = Firebase.storage
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    private val permissionCode =101 //for location permission

    //private val GALLERY_REQUEST_CODE = 100
    private lateinit var imageUri : Uri //uri for uploading to firebase
    private lateinit var testImg1: ImageView //late init is so you can initialize later which loads xml
    private lateinit var testImg1Uri: Uri // uri for 1st image preview to be uploaded
    private lateinit var testImg2: ImageView
    private lateinit var testImg2Uri: Uri // 2nd
    private lateinit var testImg3: ImageView
    private lateinit var testImg3Uri: Uri // 3rd
    //url for new post image might need to change to String Array for multiple images
    private var uploadCount = 1 //used in openGallery and activity result
    //private var postCount = 0//database post count according to city
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private var locationList: MutableList<Double> = mutableListOf(lat,long)
    private lateinit var addressString :Array<String>
    companion object{
        const val TAG = "CreatePostActivity"
        //private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
        private const val permissionCode=100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        addressString = arrayOf("","","")


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


        // Deletes the 3 image previews
        val removePhotoButton1 =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.button_removePhoto1)
        removePhotoButton1.setOnClickListener{
            previewImg1.setImageDrawable(null)
            Toast.makeText(this, "Image removed.", Toast.LENGTH_SHORT).show()
        }

        val removePhotoButton2 =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.button_removePhoto2)
        removePhotoButton2.setOnClickListener{
            previewImg2.setImageDrawable(null)
            Toast.makeText(this, "Image removed.", Toast.LENGTH_SHORT).show()
        }

        val removePhotoButton3 =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.button_removePhoto3)
        removePhotoButton3.setOnClickListener{
            previewImg3.setImageDrawable(null)
            Toast.makeText(this, "Image removed.", Toast.LENGTH_SHORT).show()
        }
        // End of deletion of the 3 image previews
        //CAMERA

        val buttonTakePhoto =
            findViewById<Button>(R.id.button_take_photo)
        buttonTakePhoto.setOnClickListener{
            //placeholder code to ask for camera privileges
        }
        val getLocationButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.locationButton)
        getLocationButton.setOnClickListener{
            //isLocationPermissionGranted()
            getLocation()

        }
        val submitPostButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.submitBtn)
        submitPostButton.setOnClickListener{
            if(addressString[1] == "")
                Toast.makeText(this,"No location. Please Try Again.",Toast.LENGTH_LONG).show()
            else
            submitPost()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null){
                currentLocation = location
                lat = currentLocation!!.latitude
                long = currentLocation!!.longitude
                locationList.set(0, lat)
                locationList.set(1,long)
                Log.d(TAG, lat.toString() + "" + long.toString())

                Handler().postDelayed(Runnable {
                    //after 3s
                    addressString = getAddress(locationList.get(0),locationList.get(1))
                }, 50)
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            permissionCode -> if (grantResults.isEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {

                getLocation()
            }
        }
    }
    private fun getAddress(lat: Double, lng: Double): Array<String> {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(lat, lng, 1)
        Log.d(TAG,list[0].getAddressLine(0))
        //Log.d(TAG,list[0].getAddressLine(1))
        //Log.d(TAG,list[0].getAddressLine(2))
        //We get a ful address starting from address, city, state, zip code, country
        //Now we must split this string to just city and state
        val stringArray= list[0].getAddressLine(0).split(",").toTypedArray()
        val onlyTheState = stringArray[2].split(" ")
        stringArray[2] = onlyTheState[1] // ["", "state", "zipcode"]
        Log.d(TAG, stringArray[2])
        return stringArray
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
//    private fun isLocationPermissionGranted(): Boolean {
//        return if (ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
//                    android.Manifest.permission.ACCESS_FINE_LOCATION,
//                    android.Manifest.permission.ACCESS_COARSE_LOCATION
//                ),
//                requestcode
//            )
//            false
//        } else {
//            true
//        }
//    }


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
    private fun uploadImage(state: String, city: String, postCount: Long): List<String> {
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
//        val docRef = db.collection("Users").document(user?.uid.toString())
//        val userString = user?.uid.toString()//gets user

        //val ifNoDirectory = userString  //Each post's photos is a new directory based on UserID
        //val newDirectory = postCount.toString()
        //val storageReference = FirebaseStorage.getInstance().getReference("postImages/$ifNoDirectory/$newDirectory/$fileName1")
        //newImageURL = "postImages/$newDirectory/$fileName" //where to upload new post photo [delete this line]
        if (testImg1.getDrawable() != null) { //if image not empty UPLOAD IT
            val storageReference = FirebaseStorage.getInstance().getReference("postImages/$state/$city/$postCount/$fileName1")
            storageReference.putFile(testImg1Uri).addOnSuccessListener {
                image1URL = "postImages/$state/$city/$postCount/$fileName1" //where to upload new post photo
                //testImg1.setImageURI(null)//THIS WILL CLEAR PREVIEW AFTER UPLOAD
                Toast.makeText(this@CreatePostActivity, "Successful Upload", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(this@CreatePostActivity, "Failed Upload", Toast.LENGTH_SHORT).show()
            }
            image1URL = "postImages/$state/$city/$postCount/$fileName1" //where to upload new post photo
            imageURLS += (image1URL)
        }
        //TEST NULL HERE
        if (testImg2.getDrawable() != null) {
            val storageReference = FirebaseStorage.getInstance().getReference("postImages/$state/$city/$postCount/$fileName2")
            storageReference.putFile(testImg2Uri)//for other images
            image2URL = "postImages/$state/$city/$postCount/$fileName2" //where to upload new post photo
            imageURLS += (image2URL)
        }
        if (testImg3.getDrawable() != null) {
            val storageReference = FirebaseStorage.getInstance().getReference("postImages/$state/$city/$postCount/$fileName3")
            storageReference.putFile(testImg3Uri)//for other image
            image3URL = "postImages/$state/$city/$postCount/$fileName3" //where to upload new post photo
            imageURLS += (image3URL)
        }

        return imageURLS
    }
    fun getSpinnerValue(): String {
        // Filter dropdown menu
        val spinner = findViewById<Spinner>(R.id.spinner_filters) as Spinner
        var selectedText = "" // as default
        val category = resources.getStringArray(R.array.filter_options)
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, category)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    selectedText = category[position]
                    Log.d(TAG, "Category Selected: ${selectedText}")
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    //Exception Error

                }
            }
            return selectedText
        } else {
            return ""
        }
    }
    // End filter dropdown menu
    fun submitPost() {

        //Tasks
        //Instances
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        //get Image url


        //get title, description, category
        var title: EditText = findViewById(R.id.postTitle) //get title
        var titleString: String = title.text.toString()
        //get description
        var desc: EditText = findViewById(R.id.textDescriptionBox)
        var descString: String = desc.text.toString()//get desc

        var categoryString: String = getSpinnerValue()

        //FOR RYAN

        //var finalLocation = {location: new Firebase.Firestore.GeoPoint(latitude,longitude)}

        //AFTER GETTING GEOPOINT YOU PLACE IT HERE TO CONVERT TO A CITY
        //getAddress(locationList.get(0),locationList.get(1))

        val city = addressString[1] //City
        val state = addressString[2] //State
        Log.d(TAG,state)
        //End of location stuff

        //Pull POST NUMBER AND INCREMENT IT FOR EACH NEW POST
        var postCount: Long = 0
        var postCountRef =
            db.collection("Items").document(state).collection(city).document("postCount")
        postCountRef.get().addOnSuccessListener { document ->
            if (document != null && document.data?.getValue("count") !=null) {
                postCount =
                    document.data?.getValue("count") as Long //casted Any? to Int. This is postCount for a city
                //
                Log.d(TAG, "Post number retrieved: $postCount")
                postCount += 1 //update locally
                Log.d(TAG, "Post count updated Locally: #$postCount")
                //update now
                postCountRef.update("count", postCount).addOnSuccessListener {
                    Log.d(
                        TAG,
                        "postCount successfully updated! : $postCount"
                    )
                }
                    .addOnFailureListener { e ->
                        Log.w(
                            TAG,
                            "Error updating postCount",
                            e
                        )
                    }//update to database
            }
            else if (document.data?.getValue("count") ==null){
                val count = hashMapOf("count" to 0)
                postCountRef.set(count)
                return@addOnSuccessListener
            }
            val postCountString = postCount.toString()
            Log.d(TAG, "Post String: $postCountString")
            //postCount.toInt()
            // upload Image and get URL it returns
            //check if image previews 1,2,3 are null or not
            var imageURLS: List<String> = uploadImage(state, city, postCount)
            //val rating = 5 //ITS FREE ITEM NO RATING give rating
            //get UserID, and that is a reference to a database location
            val user = auth.currentUser
            //val docRef = db.collection("Users").document(user?.uid.toString())
            val userString = user?.uid.toString()//gets user

            //Post Date
            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val postDate = formatter.format(now)
            Handler().postDelayed(Runnable {
                //after 3s
                Log.d(TAG,"Waiting on location, please wait...")
            }, 100)
            val postInfo = hashMapOf(
                "title" to titleString,
                "description" to descString,
                "category" to categoryString,
                "location" to locationList,
                "imageURLS" to imageURLS,
                "owner" to userString,
                "postDate" to postDate
            )

            //Going to posts database
            db.collection("Items").document(state).collection(city).document(postCountString)
                .set(postInfo).addOnSuccessListener {
                    Log.d(
                        TAG,
                        "Post succesfully submitted, post: $postCountString"
                    )
                }.addOnFailureListener { e -> Log.w(TAG, "Error writing post document to database", e) }

        }.addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }
    }
}