package com.example.smartcloset.Compare

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.R

class RecyclerAdapter(var context: Context, var itemlayout:Int, var clothData:ArrayList<Int>):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        lateinit var compareImg: ImageView
        init {
            var dialog = CompareDialog(context)
            //뷰 클릭 리스너 정의하기
            compareImg = view.findViewById(R.id.img_compare)
            view.setOnClickListener{
                //다이얼로그 띄우는 코드 작성
                //나중에 수정 필요
                var position = adapterPosition
                var clothObject = "나중엔 옷 객체를 전달해 DB에서 갖고와 보여줄 예정$position"
                dialog.showDialog(clothObject)
            }
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(itemlayout, viewGroup, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.compareText.text = clothData[position]
        viewHolder.compareImg.setImageResource(clothData[position])
    }

    override fun getItemCount()= clothData.size


}