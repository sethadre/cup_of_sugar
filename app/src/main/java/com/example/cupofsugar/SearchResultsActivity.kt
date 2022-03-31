package com.example.cupofsugar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.items_homepage.*

class SearchResultsActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val TAG = "LoginActivity"
    private lateinit var result: StringBuffer
//    private val result: StringBuffer = StringBuffer()
//    private val state = intent.getStringExtra("stateKey").toString()
//    private val city = intent.getStringExtra("cityKey").toString()
    private val state = "CA"
    private val city = "Long Beach"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        title = "Search Results"
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        //Get the Search Query from HomePage Search. "the words the user searched for"
        val searchQuery = intent.getStringExtra("searchQuery").toString()


        val docRef = db.collection("Items").document(state).collection(city)
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}") //this gets the data
//                    //Outputting users
                result = StringBuffer()
                result.append(document.data?.getValue("postTitle")).append(" ")
                textViewResult.setText(result)
            } else {
                Log.d(TAG, "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }
        val cancelActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.goBackButton)
        cancelActionButton.setOnClickListener {
            val intent = Intent(this, ItemsPageActivity::class.java)
            startActivity(intent)
            finish()
        }
        searchResult(searchQuery)
    }

    //SEARCH ALGORITHM GOES HERE
    private fun linearSearch(list: List<Any>, key: Any): Int? {
        for ((index, value) in list.withIndex()) {
            if (value == key) {
                return index //change to add to list then return at end
            }
        }
        return null
    }

    private fun searchResult(searchQuery : Any): Array<Any> {
        var results: Array<Any> = arrayOf()
        val docRef = db.collection("Items").document(state).collection(city)
//        val listOfCategory = ArrayList<Any>()
        var count = 0
        docRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val testPostRef = docRef.document(document.id)
                if (document.id != "postCount") {
                    Log.d("THIS DOC IS NOT POST COUNT", "YEA NOT POST COUNT")
                    Log.d("ATTEMPTING TO RETURN ALL 'DOCS'", "ATTEMPTING TO GET ALL DOCS")
                    Log.d(TAG, "${document.id} => ${document.data}")
                    Log.d("Document Name", document.id)
                    //We need to finish this
                    val listOfCategory = ArrayList<Any>()
                    listOfCategory.add(document.data.getValue("category") as String)
                    if(linearSearch(listOfCategory, searchQuery) != null) {
                        results[count] = document.id
                        count += 1
                    }
//                    println("Ordered list:")
//                    val someList = listOf(9, 7, "Adam", "Clark", "John", "Tim", "Zack", 6)
//                    println(someList)
//                    val name = 7
//                    val position = linearSearch(someList, name)
//                    println("${name} is in the position ${position} in the ordered List.")
//                    val name2 = "Tim"
//                    val position2 = linearSearch(someList, name2)
//                    println("${name2} is in the position ${position2} in the ordered List")
                }
            }
//            linearSearch(listOfCategory, searchQuery)
        }
        //return an array of itemIDs
        return results
    }
}
//}

