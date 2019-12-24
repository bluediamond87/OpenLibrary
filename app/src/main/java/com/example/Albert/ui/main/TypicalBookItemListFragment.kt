package com.example.Albert.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Albert.Models.BookItem

import com.example.Albert.R
import kotlinx.android.synthetic.main.search_fragment.*

open class TypicalBookItemListFragment : Fragment() {

    companion object {
        fun newInstance() = TypicalBookItemListFragment()
    }

    private lateinit var viewModel: TypicalBookItemListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.typical_book_item_list_fragment, container, false)
    }

    open fun getBookItemLayoutId():Int {
        return R.layout.list_book_item
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TypicalBookItemListViewModel::class.java)
        // TODO: Use the ViewModel
        val bookItemList = ArrayList<BookItem>()
        val adapter = BookItemRecyclerAdapter(bookItemList, getBookItemLayoutId())
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

        adapter.onBottomHit.observe(this, Observer {
            viewModel.OnBottomOfList.value = null
        })

        adapter.onBookItemClicked.observe(this, Observer {
            viewModel.OnBookItemSelected.value = it
        })

        adapter.onWishlistAction.observe(this, Observer {
            viewModel.OnBookItemWishListButton.value = it
        })

        viewModel.OnRemoveAtItem.observe(this, Observer {
            adapter.removeAt(it)
        })


    }

    val ViewModel: TypicalBookItemListViewModel
            get() = viewModel
}
