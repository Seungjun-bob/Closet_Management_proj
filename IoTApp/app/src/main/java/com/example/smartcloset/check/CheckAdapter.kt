package com.example.smartcloset.check

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.cloth_item.view.*

//class CheckAdapter(var context: Context, var itemlayout:Int, var ClothData: ArrayList<Int>):RecyclerView.Adapter<CheckAdapter.ViewHolder>(), Filterable {
class CheckAdapter(val context: Context,var itemlayout:Int, var clothlist: ArrayList<ClothData>) : RecyclerView.Adapter<CheckAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var clothImg : ImageView
        //        var clothImg = itemView?.findViewById<ImageView>(R.id.img_cloth)
        init {
            var dialog = ClothDetailDialog(context as Context)
            clothImg = itemView.img_cloth
            itemView.setOnClickListener {
                val position = adapterPosition
                var clothObject = clothlist[position]
                dialog.showdetailDia(clothObject)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(itemlayout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CheckAdapter.ViewHolder, position: Int) {
        holder.clothImg.setImageBitmap(clothlist[position].myimg)
    }

    interface OnItemClickListener {
        fun onClick(v:View, position: Int)
    }
    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun getItemCount() = clothlist.size
}

//private operator fun Int.get(position: Int): Bitmap? {
////    return clothD[position]
//}