package com.example.cupofsugar

import android.content.Intent
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.cupofsugar.databinding.ActivityMapsBinding
import android.location.Geocoder
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.widget.SearchView


import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main_page.*
import java.io.IOException






//class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback {
//
//    private lateinit var mMap: GoogleMap
//    private lateinit var binding: ActivityMapsBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_maps)
//
//        binding = ActivityMapsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//    }
//
//    fun goBackToPost(view: View) {
//        val intent = Intent(this, ItemPostActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
//}

class GoogleMapsActivity : FragmentActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    // creating a variable
    // for search view.
    private lateinit var searchView: SearchView

    private lateinit var  auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val TAG = "GoogleMapsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // initializing our search view.
        searchView = findViewById(R.id.idSearchView)

        // Obtain the SupportMapFragment and get notified
        // when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        val cancelButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            val intent = Intent(this, ItemPostActivity::class.java)
            startActivity(intent)
            finish()
        }

        // adding on query listener for our search view.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // on below line we are getting the
                // location name from search view.
                val location = searchView.query.toString()

                // below line is to create a list of address
                // where we will store the list of all address.
                var addressList = listOf<Address>()

                // checking if the entered location is null or not.
                if (location != null || location == "") {
                    // on below line we are creating and initializing a geo coder.
                    val geocoder = Geocoder(this@GoogleMapsActivity)
                    try {
                        // on below line we are getting location from the
                        // location name and adding that location to address list.
                        addressList = geocoder.getFromLocationName(location, 1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    // on below line we are getting the location
                    // from our list a first position.
                    if (addressList.isNotEmpty()) {
                        val address = addressList[0]

                        // on below line we are creating a variable for our location
                        // where we will add our locations latitude and longitude.
                        val latLng = LatLng(address.latitude, address.longitude)

                        // on below line we are adding marker to that position.
                        mMap.addMarker(MarkerOptions().position(latLng).title(location))

                        // below line is to animate camera to that position.
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        // at last we calling our map fragment to update.
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
//        db= FirebaseFirestore.getInstance()
//        auth= FirebaseAuth.getInstance()
//        val user = auth.currentUser
//        val docRef = db.collection("Items").document(user?.uid.toString())
//        docRef.get().addOnSuccessListener { document ->
//            if (document != null) {
//                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                //Outputting user
//
//                // on below line we are adding marker to that position.
//                mMap.addMarker(MarkerOptions().position(latLng).title(location))
//
//                // below line is to animate camera to that position.
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
//            } else {
//                Log.d(TAG, "No such document")
//            }
//        }
    }
//
//    fun goBackToPost(view: View) {
//        val intent = Intent(this, ItemPostActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
}