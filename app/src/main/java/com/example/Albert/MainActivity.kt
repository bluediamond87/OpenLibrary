package com.example.Albert

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.Albert.DataRepo.Repo
import com.example.Albert.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    private var currentTabIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Repo.setApplicationContext(applicationContext)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        viewPager.addOnPageChangeListener( object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currentTabIndex = position
                MessageDelivery.CurrentPage.onNext(position)

            }
        })
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
//        tabs.addOnTabSelectedListener( object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
//            override fun onTabReselected(p0: TabLayout.Tab?) {
//            }
//
//            override fun onTabUnselected(p0: TabLayout.Tab?) {
//            }
//
//            override fun onTabSelected(p0: TabLayout.Tab?) {
//                currentTabIndex = tabs.selectedTabPosition
//            }
//        })
    }

    override fun onBackPressed() {
        MessageDelivery.PopOffStack.onNext(currentTabIndex)
    }
}