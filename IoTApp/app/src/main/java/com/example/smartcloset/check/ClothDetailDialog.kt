package com.example.smartcloset.check

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import com.example.smartcloset.MainActivity
import com.example.smartcloset.R
import com.example.smartcloset.compare.Cloth
import kotlinx.android.synthetic.main.check_detail_dialog.*
import kotlinx.android.synthetic.main.cloth_detail_dialog.*
import kotlinx.android.synthetic.main.cloth_detail_dialog.img_detail
import kotlinx.android.synthetic.main.cloth_detail_dialog.txt_dialog_date
import kotlinx.android.synthetic.main.cloth_detail_dialog.txt_dialog_tag1
import kotlinx.android.synthetic.main.cloth_detail_dialog.txt_dialog_tag2

class ClothDetailDialog(context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickListener: onDialogClickListener

    interface onDialogClickListener {
        fun onClicked(name: String)
    }

    fun setOnClickListener(listener: onDialogClickListener) {
        onClickListener = listener
    }

    fun showdetailDia(cloth: ClothData) {//임시로 String 타입으로 해뒀지만 나중엔 옷 객체로 받아서 보여줘야 함
        dialog.setContentView(R.layout.check_detail_dialog)
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.img_detail.setImageBitmap(cloth.myimg)
        dialog.txt_dialog_date.text = "구매일 : "+ cloth.buydate
        dialog.txt_dialog_tag1.text = "분류 : "+cloth.mycategory
        dialog.txt_dialog_tag2.text = "색상 : "+cloth.mycolor
        dialog.show()

        val close = dialog.findViewById<Button>(R.id.close_button)

        close.setOnClickListener {
            dialog.dismiss() //dialog 닫힘
        }
    }
}

