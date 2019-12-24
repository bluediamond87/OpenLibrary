package com.example.Albert.DataRepo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.Albert.Models.BookItem

@Dao
interface BookItemDao {

    @Insert
    fun insertBook(bookItem: BookItem)

    @Insert
    fun insertBooks(bookItems:List<BookItem>)

    @Query("SELECT * FROM BookItem")
    fun getAll(): MutableList<BookItem>

    @Delete
    fun deleteBook(bookItem: BookItem)
}