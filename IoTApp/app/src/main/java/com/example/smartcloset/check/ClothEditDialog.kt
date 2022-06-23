package com.example.smartcloset.check

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.example.smartcloset.R
import com.example.smartcloset.compare.Cloth
import kotlinx.android.synthetic.main.check_edit_dialog.*
//class ClothEditDialog(context: ClothDetailDialog) {
class ClothEditDialog(context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickListener: onDialogClickListener

    interface onDialogClickListener {
        fun onClicked(name: String)
    }

//    fun setOnClickListener(listener: OnDialogClickListener){
//        onClickListener = listener
//    }

    interface OnDialogClickListener : onDialogClickListener {
        override fun onClicked(name: String) {
            Log.d("test", "clidk")
        }

    }

    fun showeditDia(cloth: String){
        dialog.setContentView(R.layout.check_edit_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.edit_date.setText(cloth)
        dialog.edit_tag1.setText(cloth)
        dialog.edit_tag2.setText(cloth)
        dialog.show()

        val cancle = dialog.findViewById<Button>(R.id.editcancle_button)
        val confirm = dialog.findViewById<Button>(R.id.confirm_button)
        val date = dialog.findViewById<EditText>(R.id.edit_date)
        val tag1 = dialog.findViewById<EditText>(R.id.edit_tag1)
        val tag2 = dialog.findViewById<EditText>(R.id.edit_tag2)

        cancle.setOnClickListener{
            dialog.dismiss()
        }

        confirm.setOnClickListener{
            //editbox 내용이 DB? 이전 Dialog에 반영되어야함
            //onclicklistener를 사용하면 오류가 떠서, 임시로 주석처리 한 후 직접 코드 작성했음.
            Log.d("test","ok")

//            onClickListener.onClicked(date.text.toString())
//            onClickListener.onClicked(tag1.text.toString())
//            onClickListener.onClicked(tag2.text.toString())
//            dialog.dismiss()
        }
    }

    fun showDialog() {
    }
}