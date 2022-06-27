package com.example.smartcloset.compare

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.compare_item.view.*

class RecyclerAdapter(var context: Context, var itemlayout:Int, var clothData:ArrayList<Bitmap>, var img_name:ArrayList<String>):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        lateinit var compareImg: ImageView
        init {
            var dialog = CompareDialog(context)
            //뷰 클릭 리스너 정의하기
            compareImg = view.img_compare
            Log.d("klimtest","viewholder")
//            view.setOnClickListener{
//                //다이얼로그 띄우는 코드 작성
//                //나중에 수정 필요
//                var position = adapterPosition
//                var clothObject = "나중엔 옷 객체를 전달해 DB에서 갖고와 보여줄 예정$position" //clothData[position] 을 기준으로 모델 조회해서 넣기
//                dialog.showDialog(clothObject)
//            }
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        Log.d("klimtest","onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(itemlayout, viewGroup, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d("klimtest","onBindViewHolder")
//        viewHolder.compareText.text = clothData[position]
        viewHolder.compareImg.setImageBitmap(clothData[position])
    }

    override fun getItemCount()= clothData.size


}