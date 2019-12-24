package com.example.Albert.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.Albert.MessageDelivery
import com.example.Albert.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.random.Random

/**
 * A placeholder fragment containing a simple view.
 */
class StackHolderFragment(
    private val nextFragment:Fragment,
    private val indexId:Int) : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private val disposableCollection = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)

        var disposable: Disposable = MessageDelivery.AddToStack.subscribe {
            if (it.tabIndex == indexId)
                replaceFragment(it.fragment)
        }
        disposableCollection.add(disposable)

        disposable = MessageDelivery.PopOffStack.subscribe {
            if( it == indexId)
                childFragmentManager.popBackStack()
        }
        disposableCollection.add(disposable)

        disposable = MessageDelivery.CurrentPage.subscribe {
            if( it == indexId)
                childFragmentManager.fragments.first().onResume()
        }
        disposableCollection.add(disposable)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onStart() {
        super.onStart()
        replaceFragment(nextFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(placeholder_root.id, fragment).addToBackStack(
            Random.Default.nextInt().toString()).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableCollection.dispose()
    }

    companion object {
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(nextFragment:Fragment, indexId: Int): StackHolderFragment {
            return StackHolderFragment(nextFragment, indexId)
        }
    }
}