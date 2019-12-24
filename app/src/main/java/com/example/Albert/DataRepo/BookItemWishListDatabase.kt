package com.example.Albert.DataRepo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.Albert.Models.BookItem

@Database(entities = arrayOf(BookItem::class), version = 1,  exportSchema = false)
abstract class BookItemWishListDatabase : RoomDatabase() {
    abstract fun bookItemDao(): BookItemDao
}