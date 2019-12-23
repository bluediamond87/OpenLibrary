package com.example.Albert.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.Albert.MessageDelivery
import com.example.Albert.R
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment(val nextFragment:Fragment) : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
        val subscribe = MessageDelivery.AddToSearchStack.subscribe {
            childFragmentManager.beginTransaction().replace(placeholder_root.id, it).commitNow()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
        childFragmentManager.beginTransaction().replace(placeholder_root.id, nextFragment).commitNow()

    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(nextFragment:Fragment): PlaceholderFragment {
            return PlaceholderFragment(nextFragment)

        }
    }
}