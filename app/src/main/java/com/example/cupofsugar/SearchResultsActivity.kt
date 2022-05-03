package com.milkcandy.cupofsugar

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
import kotlinx.android.synthetic.main.activity_homepage.*
import org.w3c.dom.Document
import org.w3c.dom.Text

class SearchResultsActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val TAG = "LoginActivity"
    private lateinit var result: StringBuffer
    private lateinit var foundItems:ArrayList<DocumentReference>
//    private val result: StringBuffer = StringBuffer()
//    private val state = intent.getStringExtra("stateKey").toString()
//    private val city = intent.getStringExtra("cityKey").toString()
    private lateinit var state:String
    private lateinit var city:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        title = "Search Results"
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        //Get the Search Query from HomePage Search. "the words the user searched for"
        val searchQuery = intent.getStringExtra("searchQuery").toString()
        state = intent.getStringExtra("stateKey").toString()
        city = intent.getStringExtra("cityKey").toString()
        val cancelActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.goBackButton)
        cancelActionButton.setOnClickListener {
            val intent = Intent(this, ItemsPageActivity::class.java)
            startActivity(intent)
            finish()
        }
        foundItems = searchResult(searchQuery)

        val textOne = findViewById<TextView>(R.id.textOne)

        //Make An array of Views
        val views: Array<TextView> = arrayOf(textOne,)

        var titlePass = " "
        //val docRef = db.collection("Items").document(state).collection(city)
        for(item in foundItems){
            var i = 0
            item.get().addOnSuccessListener { document ->
                titlePass = document.data?.getValue("title") as String
                views[i].text = titlePass //Set the string of that textView
            }
            i++
        }
        //Change this later once we get a for loop for like items (Views)
        //We also need to make Views invisible for the amount of items missing out of 10

        textOne.setOnClickListener{
            foundItems[0].get().addOnSuccessListener {
                //titlePass = it.data?.getValue("title") as String
                //textOne.text = titlePass //Set the string of that textView
                val intent = Intent(this, ItemPostActivity::class.java)
                val descriptionPass = it.data?.getValue("description") as String
                val refToPost = foundItems[0].toString()
                val ownerIDPass = it.data?.getValue("owner") as String
                intent.putExtra("titleKey", titlePass)
                intent.putExtra("descriptionKey", descriptionPass)
                intent.putExtra("ownerIDKey", ownerIDPass)

                intent.putExtra("postRefKey", refToPost)

                intent.putExtra("stateKey",state)
                intent.putExtra("cityKey",city)
                intent.putExtra("docIDKey",it.id)
                startActivity(intent)
                finish()
            }

        }

    }

    //SEARCH ALGORITHM GOES HERE


    private fun searchResult(searchQuery : String): ArrayList<DocumentReference> {
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
                    var possibleMatch: List<String> = post.split(" ")
                    for(word in possibleMatch){
                        if (word == searchQuery) {
                            listOfMatches.add(testPostRef)
                            Log.d("Post Found: ", document.id)
                        }
                        else{
                            Log.d("No match found for : ","${word}")
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

