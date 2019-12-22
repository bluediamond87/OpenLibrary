package com.example.Albert.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Albert.DataRepo.Repo
import com.example.Albert.Models.BookItem
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {

    private val disposable = CompositeDisposable()

    val onListChange = MutableLiveData<ArrayList<BookItem>>()
    val OnListChange: LiveData<ArrayList<BookItem>>
        get() = onListChange

    val onSearchUpdate = MutableLiveData<String>()
    val OnSearchUpdate:LiveData<String>
        get() = onSearchUpdate

    val onItemUpdate = MutableLiveData<Int>()
    val OnItemUpdate:LiveData<Int>
        get() = onItemUpdate

    val onAppendAdditionalItems = MutableLiveData<ArrayList<BookItem>>()
    val OnAppendAdditionalItems:LiveData<ArrayList<BookItem>>
        get() = onAppendAdditionalItems

    var searchingFor = ""

    var currentPage = 0

    fun search(searchFor:String) {
        searchingFor = searchFor
        onSearchUpdate.value = searchFor
        doFreshSearch()
    }

    fun refreshList() {

    }

    fun shutdown() {

    }

    fun fetchNextPage(page:Int) {
        currentPage = page
        doPageSearch()
    }

    fun doFreshSearch() {
        val disposable = Repo.getInstance().getBookSearch(searchingFor, 1)
            .performOnComputation()
            .toLiveData(disposable)

    }

    fun doPageSearch() {
        if (searchingFor === "") {
            onListChange.value = ArrayList()
        }
        val disposable = Repo.getInstance()
            .getBookSearch(searchingFor, currentPage)
            .performOnComputation().toLiveData(disposable)

    }

    private fun <T> Single<T>.performOnComputation(): Single<T> {
        return this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }



    /**

     * Extension function to convert a Single into a LiveData by subscribing to it.

     **/

    private fun <T> Single<T>.toLiveData(compositeDisposable: CompositeDisposable): LiveData<Result<T>> {
        return this.toObservable().toLiveData(compositeDisposable)
    }



    /**

     * Extension function to convert an Observable into a LiveData by subscribing to it.

     **/

    private fun <T> Observable<T>.toLiveData(compositeDisposable: CompositeDisposable): LiveData<Result<T>> {

        val data = MutableLiveData<Result<T>>()

        compositeDisposable.add(

            subscribe(

                {

                    // On Next

                    data.postValue(Result.success(it))

                },

                {

                    // On Error

                    data.postValue(Result.failure(it))

                },

                {

                    // On Complete

                },

                {

                    // On Subscribe

                })

        )

        return data

    }

}
