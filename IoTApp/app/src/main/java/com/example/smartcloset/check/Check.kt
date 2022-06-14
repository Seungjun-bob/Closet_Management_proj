package com.example.smartcloset.check

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.MainActivity
import com.example.smartcloset.R

class Check : Fragment() {
    lateinit var mainActivity: MainActivity
    lateinit var checkAdapter: CheckAdapter
    var clothlist = ArrayList<Int> (
//        ClothData("반팔", "black", "00"),
//        ClothData("반팔", "black", "01"),
//        ClothData("반팔", "black", "02"),
//        ClothData("반팔", "black", "03")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.checkclothes, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                Log.e("TAG", "${tab!!.position}")
//                when(tab.position){
//                    0 ->{
//                        checkAdapter.filter.filter("")
//                    }
//                    1 -> {
//                        checkAdapter.filter.filter("ShortTop")
//                    }
//                    2 -> {
//                        checkAdapter.filter.filter("LongTop")
//                    }
//                    3 -> {
//                        checkAdapter.filter.filter("GAME")
//                    }
//                    4 -> {
//                        checkAdapter.filter.filter("GAME")
//                    }
//                    5 -> {
//                        checkAdapter.filter.filter("GAME")
//                    }
//                    6 -> {
//                        checkAdapter.filter.filter("GAME")
//                    }
//                    7 -> {
//                        checkAdapter.filter.filter("GAME")
//                    }
//                }
//            }
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                tab!!.view.setBackgroundColor(Color.TRANSPARENT)
//            }
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//        })

        //RecyclerView 선언
        var checkRecyclerView: RecyclerView? = getView()?.findViewById(R.id.result_grid)
        checkRecyclerView!!.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        for(i in 0..7){
            //비교할 옷 사진 데이터들을 받아와 표시할 곳
            clothlist.add(R.drawable.p1)
        }

        //Adapter 생성하고 연결해주기
        var adapter = CheckAdapter(mainActivity, R.layout.cloth_item, clothlist)
        checkRecyclerView?.adapter = adapter

        val lm = GridLayoutManager(context, 3)
        checkRecyclerView?.layoutManager = lm
        checkRecyclerView?.setHasFixedSize(true)


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
}
//    lateinit var mainActivity: MainActivity
//    lateinit var checkAdapter: CheckAdapter
//    var datalist = ArrayList<Int>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.checkclothes, container, false)
//        val checkcategory = ArrayAdapter.createFromResource(mainActivity, R.array.cloth_category, android.R.layout.simple_spinner_dropdown_item)
//
////        checkcategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
////        view.category_spinner.adapter = checkcategory
////        view.category_spinner.setSelection(0)
//
//        return view
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        mainActivity = context as MainActivity
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        view.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                Log.e("TAG", "${tab!!.position}")
//                when(tab.position){
//                    0 ->{
//                        checkAdapter.filter.filter("")
//                    }
//                    1 -> {
//                        checkAdapter.filter.filter("ShortTop")
//                    }
//                    2 -> {
//                        checkAdapter.filter.filter("LongTop")
//                    }
//                    3 -> {
//                        checkAdapter.filter.filter("GAME")
//                    }
//                    4 -> {
//                        checkAdapter.filter.filter("GAME")
//                    }
//                    5 -> {
//                        checkAdapter.filter.filter("GAME")
//                    }
//                    6 -> {
//                        checkAdapter.filter.filter("GAME")
//                    }
//                    7 -> {
//                        checkAdapter.filter.filter("GAME")
//                    }
//                }
//            }
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                tab!!.view.setBackgroundColor(Color.TRANSPARENT)
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//        })
//
////        setSpinner()
//
//        //RecyclerView 선언
//        var checkRecyclerView: RecyclerView? = getView()?.findViewById(R.id.result_grid)
////        checkRecyclerView!!.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
////        for(i in 0..7){
////            //비교할 옷 사진 데이터들을 받아와 표시할 곳
////            datalist.add(R.drawable.p1)
////        }
//
//        //Adapter 생성하고 연결해주기
//        var adapter = CheckAdapter(mainActivity, R.layout.cloth_item, datalist)
//        checkRecyclerView?.adapter = adapter
//
//    }
//
////    private fun setSpinner() {
////        val spinner = fragmentView?.findViewById<spinner>(R.id.category_spinner)
////        ArrayAdapter.createFromResource(
////            requireContext(),
////            R.array.cloth_category,
////            android.R.layout.simple_spinner_item
////        ).also { adapter ->
////            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
////            spinner?.adapter = adapter
////        }
////        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
////            override fun onNothingSelected(parent: AdapterView<*>?) {
////            }
////            override fun onItemSelected(
////                parent: AdapterView<*>?,
////                view: View?,
////                position: Int,
////                id: Long
////            ) {type = spinner?.selectedItem.toString()
////            }
////        }
////    }
//}