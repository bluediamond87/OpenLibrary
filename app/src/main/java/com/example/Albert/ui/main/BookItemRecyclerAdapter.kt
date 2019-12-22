package com.example.Albert.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.Albert.Models.BookItem
import com.example.Albert.R
import kotlinx.android.synthetic.main.list_book_item.*
import kotlinx.android.synthetic.main.list_book_item.view.*

class BookItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}

class BookItemRecyclerAdapter(private val bookItemList:ArrayList<BookItem>) : RecyclerView.Adapter<BookItemViewHolder>() {

    val bookItemClicked = MutableLiveData<BookItem>()
    val OnBookItemClicked:LiveData<BookItem>
        get() {
            return bookItemClicked
        }

    val onBottomHit = MutableLiveData<Unit>()
    val OnBottomHit:LiveData<Unit>
        get() {
            return onBottomHit
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_book_item, parent, false)
        return BookItemViewHolder(view)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return bookItemList.size
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val bookItem = bookItemList[position]
        holder.itemView.setOnClickListener {
            bookItemClicked.value = bookItem
        }
        holder.itemView.book_item_title.text = bookItem.Title
        holder.itemView.book_item_info.text = bookItem.Info

        if(position + 1 == bookItemList.size){
            onBottomHit.value = null
        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }






}