package com.androiddesenv.opinando.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.androiddesenv.opinando.R
import com.androiddesenv.opinando.model.Review

class ListActivity: AppCompatActivity() {

    private lateinit var reviews : MutableList<Review>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ListFragment())
                .commit()
    }
}