package com.example.smartcloset.Compare

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.cloth_detail_dialog.*

class CompareDialog(context:Context){
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener:OnDialogClickListener){
        onClickListener = listener
    }

    fun showDialog(cloth:String){ //임시로 String 타입으로 해뒀지만 나중엔 옷 객체로 받아서 보여줘야 함
        dialog.setContentView(R.layout.cloth_detail_dialog)
//        dialog.img_dialog_detail.setImageResource(cloth)
        //나중엔 받아온 객체에서 이미지, 옷에 대한 태그 정보를 받아와 세팅해줄 것
        dialog.txt_dialog_date.text = cloth
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        dialog.btn_dialog_close.setOnClickListener{
            dialog.dismiss()
        }
    }
    interface OnDialogClickListener{
        fun onClicked(name:String)
    }

}