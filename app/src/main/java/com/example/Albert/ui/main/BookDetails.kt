package com.example.Albert.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.Albert.R
import com.example.Albert.ui.main.ui.bookdetails.BookDetailsFragment

class BookDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_details_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BookDetailsFragment.newInstance())
                .commitNow()
        }
    }

}
