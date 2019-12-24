package com.example.Albert.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Albert.Models.BookItem

class TypicalBookItemListViewModel : ViewModel() {

    val OnListChange = MutableLiveData<MutableList<BookItem>>()

//    val OnSearchUpdate = MutableLiveData<String>()
//
//    val OnItemUpdate = MutableLiveData<Int>()

    val OnAppendAdditionalItems = MutableLiveData<MutableList<BookItem>>()

    val OnBottomOfList = MutableLiveData<Unit>()

    val OnBookItemSelected = MutableLiveData<BookItemRecyclerAdapter.BookItemResult>()

    val OnBookItemWishListButton = MutableLiveData<BookItemRecyclerAdapter.BookItemResult>()

    val OnRemoveAtItem = MutableLiveData<Int>()

    fun removeAt(index:Int) {
        OnRemoveAtItem.value = index
    }
}
