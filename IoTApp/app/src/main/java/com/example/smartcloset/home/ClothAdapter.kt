package com.example.smartcloset.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.home.ModelWeather
import com.example.smartcloset.R
import com.example.smartcloset.compare.CompareDialog

class ClothAdapter(var context: Context, var itemlayout:Int, var clothData:ArrayList<Int>):RecyclerView.Adapter<ClothAdapter.ViewHolder>() {
    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        lateinit var clothImage: ImageView
        init {
            //뷰 클릭 리스너 정의하기
            clothImage = view.findViewById(R.id.home_recycler)
            view.setOnClickListener{
            }
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(itemlayout, viewGroup, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.clothImage.setImageResource(clothData[position])
    }

    override fun getItemCount()= clothData.size


}