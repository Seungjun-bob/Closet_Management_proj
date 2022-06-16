package com.example.smartcloset.check

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.MainActivity
import com.example.smartcloset.R

//class CheckAdapter(var context: Context, var itemlayout:Int, var ClothData: ArrayList<Int>):RecyclerView.Adapter<CheckAdapter.ViewHolder>(), Filterable {
class CheckAdapter(val context: MainActivity, val clothList: Int, var ClothData: ArrayList<Int>) : RecyclerView.Adapter<CheckAdapter.Holder>() {
    val unFilteredList = clothList
    var filteredList = clothList

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val clothImg = itemView?.findViewById<ImageView>(R.id.img_cloth)
//        val clothCategory = itemView?.findViewById<TextView>(R.id.dogBreedTv)
//        val clothColor = itemView?.findViewById<TextView>(R.id.dogAgeTv)

        fun bind (clothData: Int, context: Context) {
            /* dogPhoto의 setImageResource에 들어갈 이미지의 id를 파일명(String)으로 찾고,
            이미지가 없는 경우 안드로이드 기본 아이콘을 표시한다.*/
//            if (clothData.img != "") {
//                val resourceId = context.resources.getIdentifier(dog.photo, "drawable", context.packageName)
//                dogPhoto?.setImageResource(resourceId)
//            } else {
            clothImg?.setImageResource(R.drawable.p1) //이미지명
//            }
            /* 나머지 TextView와 String 데이터를 연결한다. */
//            dogBreed?.text = dog.breed
//            dogAge?.text = dog.age
//            dogGender?.text = dog.gender
//            itemView.setOnClickListener {
//                var dialog = context?.let { ClothDetailDialog(it) }
//                var position = adapterPosition
//                var clothObject = "나중엔 옷 객체를 전달해 DB에서 갖고와 보여줄 예정$position"
//                dialog.showdetailDia(clothObject)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.cloth_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(clothList, context)
    }

    override fun getItemCount() = ClothData.size


//    fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charString = constraint.toString()
//                filteredList = if (charString.isEmpty()) {
//                    unFilteredList
//                } else {
//                    val filteringList = clothList
//                    for (cloth in unFilteredList!!) {
//                        if (cloth!!.category == charString) filteringList.add(cloth)
//                    }
//                    filteringList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = filteredList
//                return filterResults
//            }
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                filteredList = results?.values as ArrayList<Int>
//                notifyDataSetChanged()
//            }
//        }
//    }
}
////    val unFilteredList = ClothDataList
////    var filteredList = ClothDataList
//    private var unFilteredList = ClothData
//    private var filteredList = ClothData
//
//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
//
//        lateinit var gridImg: ImageView
////        init {
////            var dialog = ClothDetailDialog(context)
////            //뷰 클릭 리스너 정의하기
////            gridImg = view.findViewById(R.id.img_cloth)
////            view.setOnClickListener{
////                //다이얼로그 띄우는 코드 작성
////                //나중에 수정 필요
////                var position = adapterPosition
////                var clothObject = "나중엔 옷 객체를 전달해 DB에서 갖고와 보여줄 예정$position"
////                dialog.showdetailDia(clothObject)
////            }
////        }
//    }
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.cloth_item, viewGroup, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
////        holder.bindArbeitData(filteredList[position])
//        val item = filteredList?.get(position)
//        viewHolder.gridImg.setImageResource(filteredList[position])
//    }
//
//    override fun getItemCount()= ClothData.size
//
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charString = constraint.toString()
//                filteredList = if (charString.isEmpty()) {
//                    unFilteredList
//                } else {
//                    val filteringList = ClothData
//                    for (item in unFilteredList!!) {
//                        if (item!!.type == charString) filteringList.add(item)
//                    }
//                    filteringList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = filteredList
//                return filterResults
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                filteredList = results?.values as ArrayList<Int>
//                notifyDataSetChanged()
//            }
//        }
//
//    }
////    override fun getFilter(): Filter {
////        return object : Filter() {
////            override fun performFiltering(constraint: CharSequence?): FilterResults {
////                val charString = constraint.toString()
////                filteredList = if (charString.isEmpty()) { //⑶
////                    unFilteredList
////                } else {
////                    var filteringList = ArrayList<ClothData>()
////                    for (item in unFilteredList) {
////                        if (item.category == charString) filteringList.add(item)
////                    }
////                    filteringList
////                }
////                val filterResults = FilterResults()
////                filterResults.values = filteredList
////                return filterResults
////            }
////
////            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
////                filteredList = results?.values as ArrayList<ClothData>
////                notifyDataSetChanged()
////            }
////        }
////    }
//}
