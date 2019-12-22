package com.example.Albert.DataRepo

import com.example.Albert.Models.BookItem
import io.reactivex.Single

interface IRepo {
    fun getBookSearch(searchFor:String): Single<ArrayList<BookItem>>
    fun getBookSearch(searchFor:String, page:Int): Single<ArrayList<BookItem>>
}