package com.example.Albert.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.Albert.Models.BookItem
import kotlinx.android.synthetic.main.book_item_detail_fragment.*
import com.example.Albert.R

class BookItemDetailFragment(private val book:BookItem) : Fragment() {

    companion object {
        fun newInstance(book: BookItem) = BookItemDetailFragment(book)
    }

    private lateinit var viewModel: BookItemDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.book_item_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookItemDetailViewModel::class.java)

        viewModel.DisplayTitle.observe(this, Observer {
            book_title.text = it
        })

        viewModel.DisplayAuthor.observe(this, Observer {
            book_author.text = it
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.setBookItem(book)
    }

}
