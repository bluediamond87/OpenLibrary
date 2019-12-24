package com.example.Albert.ui.main.BookItemListFragments

import com.example.Albert.R
import com.example.Albert.ui.main.TypicalBookItemListFragment

class SearchBookItemListFragment :
    TypicalBookItemListFragment() {

    override fun getBookItemLayoutId(): Int {
        return R.layout.list_book_item
    }
}