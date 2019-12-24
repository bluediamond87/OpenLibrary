package com.example.Albert.ui.main.BookItemListFragments

import com.example.Albert.R
import com.example.Albert.ui.main.TypicalBookItemListFragment

class WishListBookItemFragment : TypicalBookItemListFragment()
{
    override fun getBookItemLayoutId(): Int {
        return R.layout.wishlist_list_book_item
    }
}