package com.example.smartcloset.check

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.check_detail_dialog.*
import kotlinx.android.synthetic.main.check_edit_dialog.*
import kotlinx.android.synthetic.main.cloth_detail_dialog.*
import kotlinx.android.synthetic.main.cloth_detail_dialog.txt_dialog_date

class ClothDetailDialog(context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickListener: onDialogClickListener

    interface onDialogClickListener {
        fun onClicked(name: String)
    }

    fun setOnClickListener(listener: onDialogClickListener) {
        onClickListener = listener
    }

    fun showdetailDia(cloth: String) {//임시로 String 타입으로 해뒀지만 나중엔 옷 객체로 받아서 보여줘야 함
        dialog.setContentView(R.layout.check_detail_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.txt_dialog_date.text = cloth
//        dialog.txt_dialog_tag1.text = cloth
//        dialog.txt_dialog_tag2.text = cloth
        dialog.show()

        val close = dialog.findViewById<Button>(R.id.close_button)
        val edit = dialog.findViewById<Button>(R.id.edit_button)
        val delete = dialog.findViewById<Button>(R.id.delete_button)

        close.setOnClickListener {
            //dialog가 닫혀야함
            dialog.dismiss()
        }

//        edit.setOnClickListener{
//            //edit dialog가 열려야함
//            val dialog = ClothEditDialog(this)
//            dialog.showeditDia()
//            //edit dialog 에서 정의된 데이터 받아옴
////            dialog.setOnCloickedListener(object : ClothEditDialog<Any?>.OnDialogClickListener {
////                override fun onClicked(date: String, tag1: String, tag2: String)
////            })
//        }
//
//        delete.setOnClickListener{
//            //delete dialog가 열려야함
//            val dialog: AlertDialog = this@ClothDetailDialog.let {
//                val builder: AlertDialog.Builder = AlertDialog.Builder()
//                builder.apply {
//                    this.setMessage("해당 옷을 삭제하시겠습니까?")
//                    this.setCancelable(false)
//                    this.setPositiveButton("삭제") { dialog, _ ->
//                        CheckAdapter.ClothDataDelete(position)
//                        dialog.dismiss()
//                    }
//                    this.setNegativeButton("취소") { dialog, _ ->
//                        dialog.cancel()
//                    }
//                }
//                builder.create()
//            }
//            dialog.show()
//        }
//    }

        //    private fun deleteDialog(position: Int) {
//        val dialog: AlertDialog = this@ClothDetailDialog.let {
//            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
//            builder.apply {
//                this.setMessage("해당 옷을 삭제하시겠습니까?")
//                this.setCancelable(false)
//                this.setPositiveButton("삭제") { dialog, _ ->
//                    CheckAdapter.ClothDataDelete(position)
//                    dialog.dismiss()
//                }
//                this.setNegativeButton("취소") { dialog, _ ->
//                    dialog.cancel()
//                }
//            }
//            builder.create()
//        }
//        dialog.show()
    }
}

