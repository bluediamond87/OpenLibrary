package com.example.Albert

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

object MessageDelivery {

    val AddToSearchStack = PublishSubject.create<Fragment>()
}