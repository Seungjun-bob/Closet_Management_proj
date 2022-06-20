package com.example.smartcloset.login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.smartcloset.R
import com.example.smartcloset.network.BoardData
import kotlinx.android.synthetic.main.activity_http_test.*
import kotlinx.android.synthetic.main.register.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class Register: AppCompatActivity(), View.OnClickListener {
    val TAG: String = "Register"
    var isExistBlank = false
    var isPWSame = false
    var isBirthdayright = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        submit_register.setOnClickListener(this)
        back_register.setOnClickListener(this)
    }
    //*************다이얼로그 메서드**********
    fun dialog(type: String){
        val dialog = AlertDialog.Builder(this)
        // 빈 칸 있을 경우
        if(type.equals("blank")){
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("입력란을 모두 작성해주세요")
        }
        // 비밀번호 다를 경우
        else if(type.equals("not same")){
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("비밀번호가 다릅니다")
        }// 생년월일 잘못 입력했을 경우
        else if(type.equals("wrong birthday")){
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("생년월일을 잘못 입력하셨습니다.")
        }

        val dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    DialogInterface.BUTTON_POSITIVE ->
                        Log.d(TAG, "다이얼로그")
                }
            }
        }
        dialog.setPositiveButton("확인",dialog_listener)
        dialog.show()
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.submit_register -> {
                val id = id_register.text.toString()
                val pw = pw_register.text.toString()
                val pwcheck = pwcheck_register.text.toString()
                val name = name_register.text.toString()
                val birthday = birthday_register.text.toString()

                // 유저가 항목을 다 채우지 않았을 경우
                if(id.isEmpty() || pw.isEmpty() || pwcheck.isEmpty() || name.isEmpty() || birthday.isEmpty()){
                    isExistBlank = true
                }
                else{
                    if(pw == pwcheck){
                        isPWSame = true
                    }else if(birthday.length==8){
                        isBirthdayright = true
                    }
                }

                if(!isExistBlank && isPWSame){

                    // 회원가입 성공 토스트 메세지 띄우기
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()



                    // 로그인 화면으로 이동
                    val intent = Intent(this, FirstLogin::class.java)
                    startActivity(intent)

                }
                else{

                    // 상태에 따라 다른 다이얼로그 띄워주기
                    if(isExistBlank){   // 작성 안한 항목이 있을 경우
                        dialog("blank")
                    }
                    else if(!isPWSame){ // 입력한 비밀번호가 다를 경우
                        dialog("not same")
                    }
                    else if(!isBirthdayright){
                        dialog("wrong birthday")
                    }
                }

            }
            R.id.back_register -> {
                val intent = Intent(this, FirstLogin::class.java).apply {
                }
                startActivity(intent)
            }
        }
    }
}
