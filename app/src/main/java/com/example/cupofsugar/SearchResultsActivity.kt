package com.example.cupofsugar

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.items_homepage.*
import org.w3c.dom.Document

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

        val cancelActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.goBackButton)
        cancelActionButton.setOnClickListener {
            val intent = Intent(this, ItemsPageActivity::class.java)
            startActivity(intent)
            finish()
        }
        var foundItems = searchResult(searchQuery)
        val docRef = db.collection("Items").document(state).collection(city)
        for(item in foundItems){
            item.get().addOnSuccessListener { document ->
                val text = findViewById<TextView>(R.id.textOne)
                text.text = document.data?.getValue("title") as String
            }
        }

    }

    //SEARCH ALGORITHM GOES HERE
    private fun linearSearch(list: List<Any>, key: Any): ArrayList<Any> {
        val foundList = ArrayList<Any>()
        for ((index, value) in list.withIndex()) {
            if (value == key) {
                foundList.add(value) //change to add to list then return at end
            }
        }
        return foundList
    }

    private fun searchResult(searchQuery : Any): ArrayList<DocumentReference> {
        var results: Array<DocumentReference> = arrayOf()
        val docRef = db.collection("Items").document(state).collection(city)
        val listOfMatches = ArrayList<DocumentReference>()
//        var count = 0
        docRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                var testPostRef = docRef.document(document.id)
                if (document.id != "postCount") {
                    Log.d("THIS DOC IS NOT POST COUNT", "YEA NOT POST COUNT")
                    Log.d("ATTEMPTING TO RETURN ALL 'DOCS'", "ATTEMPTING TO GET ALL DOCS")
                    Log.d(TAG, "${document.id} => ${document.data}")
                    Log.d("Document Name", document.id)
                    //We need to finish this
//                    val listOfCategory = ArrayList<Any>()
                    var post = document.data.getValue("title") as String
                    var possibleMatch = arrayOf(post.split(" "))
                    for(word in possibleMatch){
                        if (searchQuery == word) {
                            listOfMatches.add(testPostRef)
                        }
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
//            results = arrayOf(linearSearch(listOfCategory, searchQuery))
//            count += 1
        }
        //return an array of itemIDs
        return listOfMatches
    }
}
//}

