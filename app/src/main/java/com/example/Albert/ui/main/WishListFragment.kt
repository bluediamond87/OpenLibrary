package com.example.Albert.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.Albert.MessageDelivery

import com.example.Albert.R
import com.example.Albert.ui.main.BookItemListFragments.WishListBookItemFragment

class WishListFragment : Fragment() {

    companion object {
        fun newInstance() = WishListFragment()
    }

    private lateinit var viewModel: WishListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wish_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WishListViewModel::class.java)




        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        val bookListFragment =
            childFragmentManager.findFragmentById(R.id.book_listing) as WishListBookItemFragment

        val listViewModel = bookListFragment.ViewModel

        viewModel.onWishListBooksUpdate.observe(this, Observer {
            listViewModel.OnListChange.value = it
        })

        viewModel.onWishListBooksAdded.observe(this, Observer {
            listViewModel.OnAppendAdditionalItems.value = it
        })

        listViewModel.OnBookItemSelected.observe(this, Observer {
            MessageDelivery.AddToStack.onNext(
                MessageDelivery.StackInfo(BookItemDetailFragment.newInstance(it.bookItem)
                    , 1)
            )
        })

        listViewModel.OnBookItemWishListButton.observe(this, Observer {
            viewModel.removeFromList(it.bookItem)
            listViewModel.removeAt(it.index)

        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshList()
    }


}
