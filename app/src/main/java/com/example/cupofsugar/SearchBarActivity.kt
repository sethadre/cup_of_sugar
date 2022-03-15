package com.example.cupofsugar

import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import com.google.android.gms.maps.GoogleMap
import java.util.ArrayList

class SearchBarActivity : AppCompatActivity() {
    // List View object
    private lateinit var listView: ListView

    // Define array adapter for ListView
    var adapter: ArrayAdapter<String>? = null

    // Define array List for List View data
    var mylist: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // initialise ListView with id
        listView = findViewById(R.id.listView)

        // Add items to Array List
        mylist = ArrayList()
        mylist!!.add("C")
        mylist!!.add("C++")
        mylist!!.add("C#")
        mylist!!.add("Java")
        mylist!!.add("Advanced java")
        mylist!!.add("Interview prep with c++")
        mylist!!.add("Interview prep with java")
        mylist!!.add("data structures with c")
        mylist!!.add("data structures with java")

        // Set adapter to ListView
        adapter = ArrayAdapter(this, R.layout.simple_list_item_1, mylist!!)
        listView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate menu with items using MenuInflator
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        // Initialise menu item search bar
        // with id and take its object
        val searchViewItem = menu.findItem(R.id.searchBar)
        val searchView: SearchView = MenuItemCompat.getActionView(searchViewItem) as SearchView

        // attach setOnQueryTextListener
        // to search view defined above
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                // Override onQueryTextSubmit method
                // which is call
                // when submitquery is searched
                override fun onQueryTextSubmit(query: String): Boolean {
                    // If the list contains the search query
                    // than filter the adapter
                    // using the filter method
                    // with the query as its argument
                    if (mylist?.contains(query) == true) {
                        adapter!!.filter.filter(query)
                    } else {
                        // Search query not found in List View
                        Toast
                            .makeText(
                                this@SearchBarActivity,
                                "Not found",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                    return false
                }

                // This method is overridden to filter
                // the adapter according to a search query
                // when the user is typing search
                override fun onQueryTextChange(newText: String): Boolean {
                    adapter!!.filter.filter(newText)
                    return false
                }
            })
        return super.onCreateOptionsMenu(menu)
    }
}