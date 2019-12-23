package com.example.Albert.ui.main

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Albert.MessageDelivery
import com.example.Albert.Models.BookItem
import kotlinx.android.synthetic.main.search_fragment.*

import com.example.Albert.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        // TODO: Use the ViewModel
        var bookItemList = ArrayList<BookItem>()
        val adapter = BookItemRecyclerAdapter(bookItemList)
        book_items_recycler.layoutManager = LinearLayoutManager(context)
        book_items_recycler.adapter = adapter
        viewModel.OnListChange.observe(this, Observer {
            adapter.BookList = it
            book_items_recycler.adapter?.notifyDataSetChanged()
        })

        viewModel.OnAppendAdditionalItems.observe(this, Observer {
            val list = adapter.BookList
            val refreshStart = adapter.BookList.size
            list.addAll(it)
            list.add(BookItem.BottomSignal)
            book_items_recycler.adapter?.notifyItemRangeChanged(refreshStart, it.size + 1)
        })

        var pageCounter = 1
        adapter.OnBottomHit.observe(this, Observer {
            ++pageCounter
            viewModel.fetchNextPage(pageCounter)
        })

        viewModel.onSearchUpdate.observe(this, Observer {
            pageCounter = 1
        })



        adapter.OnBookItemClicked.observe(this, Observer {
            MessageDelivery.AddToSearchStack.onNext(BookItemDetailFragment.newInstance(it))
//            childFragmentManager.beginTransaction().replace(placeholder_root.id,
//                BookItemDetailFragment.newInstance(it)).commitNow()
//            val intent = Intent(context, BookDetails::class.java)
//            startActivity(intent)

//            MessageDelivery.PushOnSearchStack.value = BookItemDetailFragment.newInstance(it)

//            childFragmentManager?.beginTransaction()?.replace(
//                parentFragment?.id ?: id,
//                newInstance()
//                )?.commit()
        })

        search_edit.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.search(s.toString())
            }
        })
    }

    override fun onStart() {
        super.onStart()

    }


    companion object {
        fun newInstance() = SearchFragment()
    }

}
