package com.example.smartcloset.check

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.MainActivity
import com.example.smartcloset.R
import com.example.smartcloset.compare.Cloth
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.addclothes.view.*
import kotlinx.android.synthetic.main.checkclothes.*
import kotlinx.android.synthetic.main.checkclothes.view.*
import kotlinx.android.synthetic.main.compare.view.*

class Check : Fragment() {
    lateinit var mainActivity: MainActivity
    lateinit var checkAdapter: CheckAdapter
    var datalist = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.checkclothes, container, false)

        val checkcategory = ArrayAdapter.createFromResource(mainActivity, R.array.cloth_category, android.R.layout.simple_spinner_dropdown_item)

        checkcategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.category_spinner.adapter = checkcategory
        view.category_spinner.setSelection(0)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //스피너
//        var checkspinner: Spinner? = getView()?.findViewById(R.id.category_spinner)
//        val checkspinner = resources.getStringArray(R.array.cloth_category)
//        val sadapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, checkspinner)
//        binding.checkspinner.adapter = sadapter

        //RecyclerView 선언
        var checkRecyclerView: RecyclerView? = getView()?.findViewById(R.id.result_grid)

        //Adapter 생성하고 연결해주기
        val adapter = CheckAdapter(mainActivity, R.layout.cloth_item, datalist)
        checkRecyclerView?.adapter = adapter
    }
}