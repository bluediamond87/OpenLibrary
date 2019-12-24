package com.example.Albert.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.Albert.Models.BookItem
import com.example.Albert.R
import kotlinx.android.synthetic.main.list_book_item.view.*

class BookItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}

class BookItemRecyclerAdapter
    (private var bookItemList:MutableList<BookItem>,
     @LayoutRes private var bookItemLayout:Int) : RecyclerView.Adapter<BookItemViewHolder>() {

    private val impBookItemClicked = MutableLiveData<BookItemResult>()
    val onBookItemClicked:LiveData<BookItemResult>
        get() {
            return impBookItemClicked
        }

    private val impOnBottomHit = MutableLiveData<Unit>()
    val onBottomHit:LiveData<Unit>
        get() {
            return impOnBottomHit
        }

    var BookList:MutableList<BookItem>
        get() {
            return bookItemList
        }
        set(value) {
            bookItemList = value
        }

    private val impOnWishlistAction = MutableLiveData<BookItemResult>()
    val onWishlistAction:LiveData<BookItemResult>
        get() = impOnWishlistAction

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(bookItemLayout, parent, false)
        return BookItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookItemList.size
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val bookItem = bookItemList[position]
        holder.itemView.wish_list_action.visibility = View.VISIBLE
        holder.itemView.setOnClickListener {
            impBookItemClicked.value = BookItemResult(bookItem, position)
        }

        holder.itemView.wish_list_action.setOnClickListener {
            holder.itemView.wish_list_action.visibility = View.INVISIBLE
            impOnWishlistAction.value = BookItemResult(bookItem, position)
        }

        holder.itemView.book_item_title.text = bookItem.Title
        holder.itemView.book_item_info.text = bookItem.Author

        if(position + 1 == bookItemList.size){
            impOnBottomHit.value = null
        }
    }

    fun removeAt(index: Int): Boolean  {
        try{
            bookItemList.removeAt(index)
            notifyItemRemoved(index)
        } catch (e:Exception) {
            return false
        }
        return true
    }

    data class BookItemResult(
        val bookItem: BookItem,
        val index: Int
    )
}