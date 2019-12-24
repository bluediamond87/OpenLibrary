package com.example.Albert.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.Albert.MessageDelivery
import kotlinx.android.synthetic.main.search_fragment.*

import com.example.Albert.R
import com.example.Albert.ui.main.BookItemListFragments.SearchBookItemListFragment

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
    }

    override fun onStart() {
        super.onStart()

        val findFragmentById = childFragmentManager.findFragmentById(R.id.book_listing)
        val listViewModel:TypicalBookItemListViewModel = (findFragmentById as SearchBookItemListFragment).ViewModel

        viewModel.onAppendAdditionalItems.observe(this, Observer {
            listViewModel.OnAppendAdditionalItems.value = it
        })

        viewModel.onListChange.observe(this, Observer {
            listViewModel.OnListChange.value = it
        })

        var pageCounter = 1
        listViewModel.OnBottomOfList.observe(this, Observer {
            ++pageCounter
            viewModel.fetchNextPage(pageCounter)
        })

        viewModel.onSearchUpdate.observe(this, Observer {
            pageCounter = 1
        })

        listViewModel.OnBookItemSelected.observe(this, Observer {
            MessageDelivery.AddToStack.onNext(
                MessageDelivery.StackInfo(BookItemDetailFragment.newInstance(it.bookItem),
                    0))
        })

        listViewModel.OnBookItemWishListButton.observe( this, Observer {
            viewModel.addToList(it.bookItem)
        })

        search_edit.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.search(s.toString())
            }
        })
    }



    companion object {
        fun newInstance() = SearchFragment()
    }

}
