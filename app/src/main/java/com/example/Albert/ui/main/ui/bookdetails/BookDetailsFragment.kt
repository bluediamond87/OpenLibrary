package com.example.Albert.ui.main.ui.bookdetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.Albert.R
import kotlinx.android.synthetic.main.book_details_fragment.*

class BookDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = BookDetailsFragment()
    }

    private lateinit var viewModel: BookDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.book_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookDetailsViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.DisplayTitle.observe(this, Observer {
            book_title.text = it
        })

        viewModel.DisplayAuthor.observe(this, Observer {
            book_author.text = it
        })

        viewModel
    }



}
