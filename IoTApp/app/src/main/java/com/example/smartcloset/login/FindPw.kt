package com.example.smartcloset.login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.findid.*
import kotlinx.android.synthetic.main.findpw.*
import kotlinx.android.synthetic.main.register.*
import okhttp3.*
import org.json.JSONObject
import kotlin.concurrent.thread

class FindPw: AppCompatActivity(), View.OnClickListener {
    val TAG: String = "FindPW"

    var isExistBlank = false
    var isBirthdayright = false
    var t_stringBuilder = StringBuilder()
    lateinit var show_pw:String
    var show = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.findpw)

        submit_findpw.setOnClickListener(this)
        back_findpw.setOnClickListener(this)

    }

    //*************다이얼로그 메서드**********
    fun dialog(type: String){
        val dialog = AlertDialog.Builder(this)
        // 빈 칸 있을 경우
        if(type.equals("blank")){
            dialog.setTitle("조회 실패")
            dialog.setMessage("입력란을 모두 작성해주세요")
        }
        // 생년월일 잘못 입력했을 경우
        else if(type.equals("wrong birthday")){
            dialog.setTitle("조회 실패")
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
            R.id.submit_findpw -> {
                thread {
                    var email = id_findpw.text.toString()
                    var name = name_findpw.text.toString()
                    var birthday = birthday_findpw.text.toString()
                    //db테이블에 맞게 입력 받은 생년월일 형식 변환
                    t_stringBuilder.append(birthday)
                    t_stringBuilder.insert(4,'-')
                    t_stringBuilder.insert(7,'-')
                    birthday = t_stringBuilder.toString()

                    // 유저가 항목을 다 채우지 않았을 경우
                    if (email.isEmpty() || name.isEmpty() || birthday.isEmpty()) {
                        isExistBlank = true
                    } else {
                        isExistBlank = false
                    }
                    if (birthday.length == 10) { //생일이 8자리 맞게 입력되었는지
                        isBirthdayright = true
                    } else {
                        isBirthdayright = false
                    }


                    if (!isExistBlank && isBirthdayright) {
                        //서버로 전송할 JSONObject 만들기 - 사용자가 입력한 id와 password를 담고 있음
                        var jsonobj = JSONObject()
                        jsonobj.put("email",email)
                        jsonobj.put("name",name)
                        jsonobj.put("birth",birthday)

                        // 장고 페이지 url - 나중에 수정
                        val url = "http://192.168.200.107:8000/findpw"

                        //Okhttp3라이브러리의 OkHttpClient객체를 이용해서 작업
                        val client = OkHttpClient()

                        //json데이터를 이용해서 request 처리
                        val jsondata = jsonobj.toString()
                        //서버에 요청을 담당하는 객체
                        val builder = Request.Builder()    // request객체를 만들어주는 객체 생성
                        builder.url(url)                   //Builder객체에 request할 주소(네트워크상의 주소)셋팅
                        builder.post(RequestBody.create(MediaType.parse("application/json"),jsondata)) // 요청메시지 만들고 요청메시지의 타입이 json이라고 설정
                        val myrequest: Request = builder.build() //Builder객체를 이용해서 request객체 만들기
                        //생성한 request 객체를 이용해서 웹에 request하기 - request결과로 response 객체가 리턴
                        val response: Response = client.newCall(myrequest).execute()

                        //response에서 메시지꺼내서 로그 출력하기
                        val result:String? = response.body()?.string()
                        Log.d("http",result!!)
                        //로그인 성공여부가 메시지로 전달되면 그에 따라 다르게 작업할 수 있도록 코드
                        var login_result = result.split(':')
                        if(login_result[1]=="okay") {
                            // 성공 토스트 메세지 띄우기
                            runOnUiThread {
                                Toast.makeText(this, "PW찾기 성공", Toast.LENGTH_SHORT).show()
                            }
                            show_pw = login_result[2]
                            show = true
                        }
                        else if(login_result[1]=="fail") {
                            // 실패 토스트 메세지 띄우기
                            runOnUiThread {
                                Toast.makeText(this, "PW찾기 실패", Toast.LENGTH_SHORT).show()
                            }
                        }

                    } else {
                        // 상태에 따라 다른 다이얼로그 띄워주기
                        if (isExistBlank) {   // 작성 안한 항목이 있을 경우
                            runOnUiThread {
                                dialog("blank")
                            }
                        }else if (!isBirthdayright) {
                            runOnUiThread {
                                dialog("wrong birthday")
                            }
                        }
                    }
                }
                if(show) {
                    show_findpw.text = show_pw
                }
            }
            R.id.back_findpw -> {
                val intent = Intent(this, FirstLogin::class.java).apply {
                }
                startActivity(intent)
            }
        }
    }

}