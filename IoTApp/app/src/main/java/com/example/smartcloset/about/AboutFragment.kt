package com.example.smartcloset.about

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.fragment_about.view.*


class AboutFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        view.viewPager_about.adapter = ViewPagerAdapter(getAboutList())
        view.viewPager_about.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return view
    }

    private fun getAboutList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.p1, R.drawable.p1, R.drawable.p1)
    }


}