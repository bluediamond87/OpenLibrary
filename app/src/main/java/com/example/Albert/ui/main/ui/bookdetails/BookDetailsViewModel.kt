package com.example.Albert.ui.main.ui.bookdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Albert.Models.BookItem

class BookDetailsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val displayTitle = MutableLiveData<String>()
    val DisplayTitle: LiveData<String>
        get() = displayTitle

    val displayAuthor = MutableLiveData<String>()
    val DisplayAuthor: LiveData<String>
        get() = displayAuthor

    val displayInfo = MutableLiveData<String>()
    val DisplayInfo: LiveData<String>
        get() = displayInfo

    fun setBookItem(bookItem: BookItem) {
        displayTitle.value = bookItem.Title
        displayAuthor.value = bookItem.Author
        displayInfo.value = bookItem.Info
    }


}
