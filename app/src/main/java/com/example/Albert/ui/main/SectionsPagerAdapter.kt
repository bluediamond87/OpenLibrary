package com.example.Albert.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.Albert.MessageDelivery
import com.example.Albert.R
import kotlinx.android.synthetic.main.*

private val TAB_TITLES = arrayOf(
    R.string.search_title,
    R.string.wishlist_title
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, private val lifecycle: LifecycleOwner, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                PlaceholderFragment.newInstance(SearchFragment.newInstance())
            }
            else -> WishListFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }


}