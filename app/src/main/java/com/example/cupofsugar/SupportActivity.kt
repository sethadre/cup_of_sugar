package com.example.cupofsugar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        //add fragment
        if (findViewById<FrameLayout>(R.id.frameLayout) != null)
            supportFragmentManager.beginTransaction().add(R.id.frameLayout, TopicFragment()).commit()
    }
}