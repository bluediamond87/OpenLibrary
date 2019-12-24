package com.example.Albert.DataRepo

import com.example.Albert.Models.BookItem
import io.reactivex.Single

interface IRepo {
    fun getBookSearch(searchFor:String): Single<ArrayList<BookItem>>
    fun getBookSearch(searchFor:String, page:Int): Single<ArrayList<BookItem>>
    fun getAllWishlist(): Single<MutableList<BookItem>>
    fun deleteFromWishlist(bookItem: BookItem): Single<Boolean>
    fun addToWishlist(bookItem: BookItem): Single<Boolean>
}