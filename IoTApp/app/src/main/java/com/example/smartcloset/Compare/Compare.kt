package com.example.smartcloset.Compare

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.Main.MainActivity
import com.example.smartcloset.R

class Compare: Fragment() {
    var datalist =ArrayList<Int>()
    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.compare, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Fragment에선 Activity에서 사용하는 메소드들을 사용할 수 없기 때문에 onAttach(프레그먼트가 액티비티에 붙여지는 생명주기)에서
        //Context를 받아와 MainActivity로 캐스팅해주면 메소드들을 사용할 수 있다.
        //여기에서는 Adapter를 생성할 때 context를 넘겨주기 위해 이 방법을 사용했다.
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //RecyclerView 선언
        var compareRecyclerView:RecyclerView? = getView()?.findViewById(R.id.compare_recycler)
        for(i in 0..7){
            //비교할 옷 사진 데이터들을 받아와 표시할 곳
            datalist.add(R.drawable.p1)
        }

        //Adapter 생성하고 연결해주기
        val adapter =RecyclerAdapter(mainActivity, R.layout.compare_item,datalist)
        compareRecyclerView?.adapter = adapter

    }
}