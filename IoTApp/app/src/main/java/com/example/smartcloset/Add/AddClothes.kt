package com.example.smartcloset.Add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.addclothes.*

class AddClothes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addclothes)
        camera_addclothes.setOnClickListener{
            Toast.makeText(this,"카메라 버튼입니다", Toast.LENGTH_SHORT).show()
        }
        album_addclothes.setOnClickListener{
            Toast.makeText(this,"앨범 버튼입니다",Toast.LENGTH_SHORT).show()
        }
        cancel_addclothes.setOnClickListener{
            Toast.makeText(this,"취소 버튼입니다",Toast.LENGTH_SHORT).show()
        }
        save_addclothes.setOnClickListener{
            Toast.makeText(this,"저장 버튼입니다",Toast.LENGTH_SHORT).show()
        }
        val myadapter = ArrayAdapter.createFromResource(this, R.array.tagdata1, android.R.layout.simple_spinner_item)
//        val autoAdapter = ArrayAdapter.createFromResource(this,R.array.tagdata1,android.R.layout.simple_spinner_item)

        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tag1.adapter =myadapter

        tag1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tag1txt.text = (view as TextView).text
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
//                result.text = "선택된 가수가 없습니다."
            }

        }


    }
}