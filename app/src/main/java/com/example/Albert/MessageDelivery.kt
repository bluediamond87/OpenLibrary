package com.example.Albert

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

object MessageDelivery {

    data class StackInfo(val fragment: Fragment, val tabIndex: Int)
    val AddToStack = PublishSubject.create<StackInfo>()
    val PopOffStack = PublishSubject.create<Int>()
    val CurrentPage = PublishSubject.create<Int>()
}