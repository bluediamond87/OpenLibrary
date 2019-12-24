package com.example.Albert.Models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class BookItem (
    @PrimaryKey var bookId:String = "",

    @ColumnInfo(name = "book_image_url")
     val bookImageUrl:String,

    @ColumnInfo(name = "book_title")
     val bookTitle:String,

    @ColumnInfo(name = "book_author")
     val bookAuthor:String,

    @ColumnInfo(name = "book_info")
     val bookInfo:String
) {
    @Ignore
    var imageLoaded:((Bitmap) -> Unit)? = null

    @Ignore
    var coverImage:Bitmap? = null

    @Ignore
    var Info:String = bookInfo
        get() = bookInfo

    @Ignore
    var Title:String = bookTitle
        get() = bookTitle

    @Ignore
    var Author:String = bookAuthor
        get() = bookAuthor

    @Ignore
    var Cover:Bitmap? = null

    companion object {
        val BottomSignal:BookItem = BookItem("","", "", "", "")
    }
}