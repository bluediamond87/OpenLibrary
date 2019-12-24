package com.example.Albert.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Albert.DataRepo.Repo
import com.example.Albert.Models.BookItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {

    private val disposableCollection = CompositeDisposable()

    private val listChange = MutableLiveData<ArrayList<BookItem>>()
    val onListChange: LiveData<ArrayList<BookItem>>
        get() = listChange

    private val searchUpdate = MutableLiveData<String>()
    val onSearchUpdate:LiveData<String>
        get() = searchUpdate

    private val appendAdditionalItems = MutableLiveData<ArrayList<BookItem>>()
    val onAppendAdditionalItems:LiveData<ArrayList<BookItem>>
        get() = appendAdditionalItems

    var searchingFor = ""
    var currentPage = 1

    fun search(searchFor:String) {
        searchingFor = searchFor
        searchUpdate.value = searchFor
        doFreshSearch()
    }

    fun fetchNextPage(page:Int) {
        currentPage = page
        doPageSearch()
    }

    private fun doFreshSearch() {
        if (searchingFor === "" || searchingFor.length == 1) {
            listChange.value = ArrayList()
            return
        }
        val disposable = Repo.getInstance().getBookSearch(searchingFor, 1)
            .performOnComputation().toObservable().subscribe {
                listChange.value = it
            }
//        disposable.dispose()
    }

    fun doPageSearch() {
        if (searchingFor === "" || searchingFor.length == 1) {
            listChange.value = ArrayList()
            return
        }
        val disposable = Repo.getInstance()
            .getBookSearch(searchingFor, currentPage)
            .performOnComputation().toObservable().subscribe {
                appendAdditionalItems.value = it
            }
//        disposable.dispose()

    }

    fun addToList(bookItem: BookItem) {
        val subscribe = Repo.getInstance().addToWishlist(bookItem)
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

}
