package com.example.cupofsugar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SearchResultsActivity: AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val TAG= "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        title="Search Results"
        db = FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
        //Get the Search Query from HomePage Search
        val searchQuery = intent.getStringExtra("searchQuery").toString()

        val cancelActionButton =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.goBackButton)
        cancelActionButton.setOnClickListener {
            val intent = Intent(this, ItemsPageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    //SEARCH ALGORITHM GOES HERE
        private fun linearSearch(list:List<Any>, key:Any):Int?{
            for ((index, value) in list.withIndex()) {
                if (value == key){
                    return index
                }
            }
            return null
        }

//        fun main(args: Array<String>) {
//            println("
//                    Ordered list:")
//            val someList = listOf(9, 7, "Adam", "Clark", "John", "Tim", "Zack", 6)
//            println(someList)
//            val name = 7
//            val position = linearSearch(someList, name)
//            println("${name} is in the position ${position} in the ordered List.
//                ")
//
//                val name2 = "Tim"
//            val position2 = linearSearch(someList, name2)
//            println("${name2} is in the position ${position2} in the ordered List.
//                ")
//        }
}