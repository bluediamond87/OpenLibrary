package com.example.Albert.Models

import android.graphics.Bitmap

class BookItem (
    val bookImageUrl:String,
    val bookTitle:String,
    val bookInfo:String
) {
    var imageLoaded:((Bitmap) -> Unit)? = null
    var coverImage:Bitmap? = null

    var Info:String = bookInfo
        get() = bookInfo

    var Title:String = bookTitle
        get() = bookTitle

    var Cover:Bitmap? = null

    companion object {
        val BottomSignal:BookItem = BookItem("", "", "")
    }
}