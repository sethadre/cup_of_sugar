package com.example.cupofsugar

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.items_homepage.*
import org.json.JSONArray;
import org.json.JSONException
import org.json.JSONObject;

class ItemsPageActivity : AppCompatActivity() {

    //Firebase init
    private lateinit var auth: FirebaseAuth
    private  lateinit var db: FirebaseFirestore
    companion object{
        const val TAG = "ItemsPageActivity"
        const val permissionCode = 101
    }
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private var locationList: MutableList<Double> = mutableListOf(lat,long)
    private lateinit var addressString :Array<String>
    private lateinit var userState:String
    private lateinit var userCity:String
    //Grid
    val postint = 0
    private lateinit var gridView: GridView
    private var posts = arrayOf("Post0")
    private var postImages = intArrayOf(postint)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        //Front End for Grid
        //gridView = findViewById()


        //Start of Back End Stuff

        userState = "CA" //user.getState()
        userCity = "Long Beach" //user.getState()
        addressString = arrayOf("","","")
        val storage = Firebase.storage
        //This is how we download to memory and not as local file
        val ONE_MEGABYTE: Long = 1024 * 1024

//        val iv1 = findViewById<ImageView>(R.id.postView1)
//        val iv2 = findViewById<ImageView>(R.id.postView2)
//        val iv3 = findViewById<ImageView>(R.id.postView3)

        //Image View array

        //val IMGS = arrayListOf<ImageView>(iv1, iv2, iv3)

        //IMGS += iv4
        //adds iv4 to IMGS array

        //TODO GRAB STUFF FROM DB


        //this pulls the email from the database

        //Firebase declare
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val docRef = db.collection("Users").document(user?.uid.toString())
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}") //this gets the data
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


        //ITEM POST DATA
        //ITEM POST DATA
        //ITEM POST DATA
        //ITEM POST DATA
        //ITEM POST DATA
        //put this in a loop

        //LOOP
        //Grab folder refs

        val cityRef = db.collection("Items").document(userState).collection(" " + userCity)

        //Log.d("testPostRef",testPostRef.toString())

        //val testPostRef = cityRef.document(document.id)

        cityRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val testPostRef = cityRef.document(document.id)
                if(document.id != "postCount")
                {
                    Log.d("THIS DOC IS NOT POST COUNT","YEA NOT POST COUNT")
                    Log.d("ATTEMPTING TO RETURN ALL 'DOCS'","ATTEMPTING TO GET ALL DOCS")
                    Log.d(TAG, "${document.id} => ${document.data}")
                    Log.d("Document Name",document.id)


                    testPostRef.get().addOnSuccessListener { document ->
                        if (document != null) {
                            Log.d(TAG, "DocumentSnapshot data: ${document.data}\n\n") //this gets the data
                            val imgURLResult: StringBuffer = StringBuffer()

                            val url0 = (document.data?.get("imageURLS") as List<String>)[0]


                            val postPath1 = storage.reference.child(url0)

                            postPath1.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                                val bitmap1 = BitmapFactory.decodeByteArray(it, 0, it.size)
                                val imageView = ImageView(this)
                                imageView.layoutParams = TableLayout.LayoutParams(400, 400)
                                val layout = findViewById<TableLayout>(R.id.tableLayout)
                                layout?.addView(imageView)
                                imageView.setImageBitmap(bitmap1)


                                val category: StringBuffer = StringBuffer()
                                category.append(document.data?.getValue("category")).append(" ")
                                Log.d("category of doc", "${document.id}$category.toString()")

                                val description: StringBuffer = StringBuffer()
                                description.append(document.data?.getValue("description"))
                                    .append(" ")
                                Log.d("description", description.toString())

                                val ownerID: StringBuffer = StringBuffer()
                                ownerID.append(document.data?.getValue("owner"))
                                Log.d("owner", ownerID.toString())

                                val title: StringBuffer = StringBuffer()
                                title.append(document.data?.getValue("title")).append(" ")

                                Log.d("title", title.toString())
                                imageView.setOnClickListener {
                                    val intent = Intent(this, ItemPostActivity::class.java)
                                    val titlePass = title.toString()
                                    intent.putExtra("titleKey", titlePass)
                                    val descriptionPass = description.toString()
                                    intent.putExtra("descriptionKey", descriptionPass)
                                    val ownerIDPass = ownerID.toString()
                                    intent.putExtra("ownerIDKey", ownerIDPass)
                                    val refToPost = testPostRef.toString()
                                    intent.putExtra("postRefKey", refToPost)
                                    intent.putExtra("stateKey", userState)
                                    intent.putExtra("cityKey", " " + userCity)
                                    intent.putExtra("docIDKey", document.id)
                                    startActivity(intent)
                                    finish()

                                }
                            }


                        } else {
                            Log.d(TAG, "No such document")
                        } }.addOnFailureListener{ exception ->
                        Log.w(TAG, "Error Getting Docs: ", exception) }
                }
                else{
                    Log.d(TAG, "No such document")
                }
            }
        }.addOnFailureListener{ exception ->
                Log.w(TAG, "Error Getting Docs: ", exception) }




//        //HARD CODED ONE PIC
//        testPostRef.get().addOnSuccessListener { document ->
//            if (document != null) {
//                Log.d(TAG, "DocumentSnapshot data: ${document.data}\n\n") //this gets the data
//                val imgURLResult: StringBuffer = StringBuffer()
//
//                val url0 = (document.data?.get("imageURLS") as List<String>)[0]
//                testTextView.setText(url0)
//
//                val postPath1 = storage.reference.child(url0)
//
//                postPath1.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//                    val bitmap1 = BitmapFactory.decodeByteArray(it, 0, it.size)
//                    val imageView = ImageView(this)
//                    imageView.layoutParams = TableLayout.LayoutParams(400, 400)
//                    val layout = findViewById<TableLayout>(R.id.tableLayout)
//                    layout?.addView(imageView)
//                    imageView.setImageBitmap(bitmap1)
//
//                    val category: StringBuffer = StringBuffer()
//                    category.append(document.data?.getValue("category")).append(" ")
//                    Log.d("category", category.toString())
//                    val description: StringBuffer = StringBuffer()
//                    description.append(document.data?.getValue("description")).append(" ")
//                    Log.d("description", description.toString())
//                    val image0 = (document.data?.get("imageURLS") as List<String>)[0]
//                    Log.d("1st Image URL: ", image0)
//                    val image1 = (document.data?.get("imageURLS") as List<String>)[1]
//                    Log.d("2nd Image URL: ", image1)
//                    val image2 = (document.data?.get("imageURLS") as List<String>)[2]
//                    Log.d("3rd Image URL: ", image2)
//                    val ownerID: StringBuffer = StringBuffer()
//                    ownerID.append(document.data?.getValue("owner"))
//                    Log.d("owner", ownerID.toString())
//                    val title: StringBuffer = StringBuffer()
//                    title.append(document.data?.getValue("title")).append(" ")
//                    Log.d("title",title.toString())
//
//                    imageView.setOnClickListener {
//                        val intent = Intent(this, ItemPostActivity::class.java)
//                        val titlePass = title.toString()
//                        intent.putExtra("titleKey",titlePass)
//                        intent.putExtra("img0Key", image0)
//                        intent.putExtra("img1Key", image1)
//                        intent.putExtra("img2Key", image2)
//                        val descriptionPass = description.toString()
//                        intent.putExtra("descriptionKey",descriptionPass)
//                        val ownerIDPass = ownerID.toString()
//                        intent.putExtra("ownerIDKey",ownerIDPass)
//                        val refToPost = testPostRef.toString()
//                        intent.putExtra("postRefKey",refToPost)
//                        intent.putExtra("stateKey",userState)
//                        intent.putExtra("cityKey",userCity)
//                        intent.putExtra("docIDKey",document.id)
//                        startActivity(intent)
//                        finish()
//                    }
//
//                }
//
//            } else {
//                Log.d(TAG, "No such document")
//            } }.addOnFailureListener{ exception ->
//                    Log.w(TAG, "Error Getting Docs: ", exception) }
//



//        val itemRef = db.collection("Items").document("Long Beach").collection("2").document("2")
//
//        val string1 = itemRef.toString()
//        val string2 = docRef.toString()
       // val photoRef = db.collection("Items").document(itemRef.toString()).collection("postImages/post#2022_02_23_17_28_37/2022_02_23_17_28_37")
//        Log.d("message 1","*********this is a message***********************")
//        Log.i("msg2",string1)
//        Log.i("msg2",string2)

//        itemRef.get().addOnSuccessListener { document ->
//            if (document != null) {
//                Log.d(TAG, "DocumentSnapshot data: ${document.data}\n\n") //this gets the data
////                result.append(document.data?.getValue("email")).append(" ")
//                val imgURLResult: StringBuffer = StringBuffer()
//
//                val url0 = (document.data?.get("imageURLS") as List<String>)[0]
//                testTextView.setText(url0)
//
//                val postPath1   = storage.reference.child(url0)
//
//                postPath1.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//                val bitmap1 = BitmapFactory.decodeByteArray(it,0,it.size)
//                findViewById<ImageView>(R.id.postView1).setImageBitmap(bitmap1)
//                }
//
//            } else {
//                Log.d(TAG, "No such document")
//            }
//        }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
        // end of firebase stuff

        setContentView(R.layout.items_homepage) //moved this line lower

        //
        locationSpinners()
        //Search Text Field itself


        val searchActionButton =
            //Button Action
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.searchButton)
        searchActionButton.setOnClickListener {

            val searchText= findViewById<EditText>(R.id.searchTextField)
            var searchQuery = searchText.text.toString()
            Log.d(TAG,"This is your search query:  ${searchQuery}")
            searchText.addTextChangedListener{
                searchQuery = searchText.text.toString()
            }
            val intent = Intent(this, SearchResultsActivity::class.java)
            intent.putExtra("searchQuery", searchQuery)
            intent.putExtra("stateKey", userState)
            intent.putExtra("cityKey", " " +userCity)
            //intent.putExtra("docIDKey", docID)
            startActivity(intent)
            finish()
        }

        val createPostActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.createPostActionButton)
        createPostActionButton.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
            finish()
        }
        val getLocationButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.getLocationButton)
        getLocationButton.setOnClickListener{
            //isLocationPermissionGranted()
            getLocation()
            //userState = addressString[1] //city
            // userCity = addressString[2]  //state
        }


        // BOTTOM BAR BUTTONS
        // ****************************************************************************************
        // ****************************************************************************************
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


        //We are home we dont need functionality

//        val HomeActionButton =
//            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.homeActionButton)
//        HomeActionButton.setOnClickListener {
//            val intent = Intent(this, ItemsPageActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        val helpActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.helpActionButton)
        helpActionButton.setOnClickListener {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
            finish()
        }

        val settingsActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.settingsActionButton)
        settingsActionButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        // ****************************************************************************************
        // ****************************************************************************************
        // END BOTTOM BAR BUTTONS

    }
    private fun getAddress(lat: Double, lng: Double): Array<String> {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(lat, lng, 1)
        Log.d(CreatePostActivity.TAG,list[0].getAddressLine(0))
        //Log.d(TAG,list[0].getAddressLine(1))
        //Log.d(TAG,list[0].getAddressLine(2))
        //We get a ful address starting from address, city, state, zip code, country
        //Now we must split this string to just city and state
        val stringArray= list[0].getAddressLine(0).split(",").toTypedArray()
        val onlyTheState = stringArray[2].split(" ")
        stringArray[2] = onlyTheState[1] // ["", "state", "zipcode"]
        Log.d(CreatePostActivity.TAG, stringArray[2])
        return stringArray
    }


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
        @SuppressLint("MissingPermission")
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null){
                currentLocation = location
                lat = currentLocation!!.latitude
                long = currentLocation!!.longitude
                locationList.set(0, lat)
                locationList.set(1,long)
                Log.d(CreatePostActivity.TAG, lat.toString() + "" + long.toString())

                Handler().postDelayed(Runnable {
                    //after 3s
                    addressString = getAddress(locationList.get(0),locationList.get(1))
                    userState = addressString[2]
                    userCity = addressString[1]
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


    //firebase stuff
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
    //end of firebase stuff



//    fun logout(view: View){
//        val intent= Intent(this,LoginActivity::class.java)
//        startActivity(intent)
//        finish()
//
//    }
    fun goToPost(view: View) {
        val intent = Intent(this, ItemPostActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToHome(view: View) {
        // already home
    }
    fun locationSpinners(){
        fun AssetManager.readFile(fileName: String) = open(fileName)
            .bufferedReader()
            .use { it.readText() }
        var context: Context = this
        val jsonString = context.assets.readFile("US_States_and_Cities.json")
        val statesArray = arrayOf("Alaska", "Alabama", "Arkansas", "American Samoa", "Arizona", "California", "Colorado", "Connecticut", "District of Columbia", "Delaware", "Florida", "Georgia", "Guam", "Hawaii", "Iowa", "Idaho", "Illinois", "Indiana", "Kansas", "Kentucky", "Louisiana", "Massachusetts", "Maryland", "Maine", "Michigan", "Minnesota", "Missouri", "Mississippi", "Montana", "North Carolina", "North Dakota", "Nebraska", "New Hampshire", "New Jersey", "New Mexico", "Nevada", "New York", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Puerto Rico", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Virginia", "Virgin Islands", "Vermont",
            "Washington", "Wisconsin", "West Virginia","Wyoming")
        val stateSpinner = findViewById<Spinner>(R.id.spinner_state) as Spinner
        val citySpinner = findViewById<Spinner>(R.id.spinner_city) as Spinner
        var citiesList: MutableList<String> = mutableListOf("")
        //var citySpinnerAdapterEmpty = ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf(""))
        var citySpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, citiesList)
        if (stateSpinner != null) {
            var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statesArray)
            stateSpinner.adapter = adapter
        }
            stateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)
                            {

                                try {
                                    // get JSONObject from JSON file
                                    val obj = JSONObject(jsonString)
                                    //val array = JSONArray(obj)
                                    //Remaining JSON Stuff

                                    // For loop and set adapter based on selected statesArray[n]
                                        // if n, set city adapter to jsonState.getString(0 -> length)
                                    val jsonState: JSONArray = obj.getJSONArray(statesArray[position])//get selected state
                                    Log.d(TAG,statesArray[position])
                                    var cities:Array<String> = Array<String>(jsonState.length()) {""}
                                    for (city in 0 until jsonState.length()-1) {
                                        // get city
                                        cities[city] = jsonState.getString(city)
                                        citiesList.add(cities[city])
                                        Log.d(TAG,jsonState.getString(city))
                                        //citySpinner.setSelection(city)
                                        citySpinner.adapter = citySpinnerAdapter
                                    }
                                    //citiesArray = cities



                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }

                            }
                            override fun onNothingSelected(parent: AdapterView<*>) {
                            //Exception Error
                            }

                        }
//        if (citySpinner != null) {
//            //define ctitesList
//            citiesList = mutableListOf("")
//            val citySpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, citiesList)
//            citySpinner.adapter = citySpinnerAdapter
//
////            citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
////                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)
//               }
                    }






}

// ****************************                                         ***************************
// ****************************   OLD CODE KEEP FOR ADRIAN's REFERENCE  ***************************
// ****************************                                         ***************************

//HARDCODED MANUALLY GRABBED
//TODO : Array? or something to autoload images based on tags or distance
//        val postPath1   = storage.reference.child("postImages/post0/tomato.jpg")
//        val postPath2   = storage.reference.child("postImages/post0/cake.jpg")
//        val postPath3   = storage.reference.child("postImages/post0/candy.jpg")
//
//        postPath1.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//            val bitmap1 = BitmapFactory.decodeByteArray(it,0,it.size)
//            findViewById<ImageView>(R.id.postView1).setImageBitmap(bitmap1)
//        }
//        postPath2.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//            val bitmap2 = BitmapFactory.decodeByteArray(it,0,it.size)
//            findViewById<ImageView>(R.id.postView2).setImageBitmap(bitmap2)
//        }
//        postPath3.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//            val bitmap3 = BitmapFactory.decodeByteArray(it,0,it.size)
//            findViewById<ImageView>(R.id.postView3).setImageBitmap(bitmap3)
//        }


//Also hard coded clicks
//TODO: automate it

//        val postClick1  = findViewById<ImageView>(R.id.postView1)
//        val postClick2  = findViewById<ImageView>(R.id.postView2)
//        val postClick3  = findViewById<ImageView>(R.id.postView3)
//
//        postClick1.setOnClickListener {
//            Toast.makeText(this,"Image 1 Clicked",Toast.LENGTH_SHORT).show()
//        }
//        postClick2.setOnClickListener {
//            Toast.makeText(this,"Image 1 Clicked",Toast.LENGTH_SHORT).show()
//        }
//        postClick3.setOnClickListener {
//            Toast.makeText(this,"Image 1 Clicked",Toast.LENGTH_SHORT).show()
//        }



//        imageClick9.setOnClickListener {
//            //Right now theyre toast messages,
//            //Need to replace the toasts with codes to open the post info
//            Toast.makeText(this,"Image clicked, I lied", Toast.LENGTH_SHORT).show()
//        }
//        imageClick10.setOnClickListener {
//            Toast.makeText(this,"Image clicked, such Lollipop", Toast.LENGTH_SHORT).show()
//        }
//        imageClick11.setOnClickListener {
//            Toast.makeText(this,"Image clicked, MAXIMUM TOMATER", Toast.LENGTH_SHORT).show()
//        }
//        imageClick12.setOnClickListener {
//            Toast.makeText(this,"Image clicked, the cake is a lie", Toast.LENGTH_SHORT).show()
//        }
