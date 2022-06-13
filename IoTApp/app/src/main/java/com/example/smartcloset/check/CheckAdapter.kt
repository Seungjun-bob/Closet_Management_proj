package com.example.smartcloset.check

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.R
import com.example.smartcloset.compare.Cloth

class CheckAdapter(var context: Context, var itemlayout:Int, var ClothData: ArrayList<Int>):RecyclerView.Adapter<CheckAdapter.ViewHolder>(), Filterable {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        lateinit var gridImg: ImageView
        init {
            var dialog = ClothDetailDialog(context)
            //뷰 클릭 리스너 정의하기
            gridImg = view.findViewById(R.id.img_cloth)
            view.setOnClickListener{
                //다이얼로그 띄우는 코드 작성
                //나중에 수정 필요
                var position = adapterPosition
                var clothObject = "나중엔 옷 객체를 전달해 DB에서 갖고와 보여줄 예정$position"
                dialog.showdetailDia(clothObject)
            }
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cloth_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.compareText.text = clothData[position]
        viewHolder.gridImg.setImageResource(ClothData[position])
    }

    override fun getItemCount()= ClothData.size

    override fun getFilter(): Filter {
        TODO("Not yet impleme   nted")
    }

}
