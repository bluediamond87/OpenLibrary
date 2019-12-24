package com.example.Albert.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Albert.DataRepo.Repo
import com.example.Albert.Models.BookItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WishListViewModel : ViewModel() {
    fun refreshList() {
        val subscribe = Repo.getInstance().getAllWishlist()
            .performOnComputation()
            .toObservable()
            .subscribe(
                { result -> onWishListBooksUpdate.value = result },
                { error -> }
            )
//        subscribe.dispose()
    }


    fun removeFromList(bookItem: BookItem) {
        val subscribe = Repo.getInstance().deleteFromWishlist(bookItem)
            .performOnComputation()
            .toObservable()
            .subscribe(
                { result ->
                    if (result) {
                        val list = ArrayList<BookItem>()
                        list.add(bookItem)

                    }
                },
                { error -> }
            )
//        subscribe.dispose()
    }



    private fun <T> Single<T>.performOnComputation(): Single<T> {
        return this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }

    val onWishListBooksUpdate = MutableLiveData<MutableList<BookItem>>()
    val onWishListBooksAdded = MutableLiveData<MutableList<BookItem>>()
    val onWishListBooksRemoved = MutableLiveData<MutableList<BookItem>>()
}
