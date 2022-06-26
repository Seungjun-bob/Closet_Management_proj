package com.example.smartcloset.login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.smartcloset.R
import com.example.smartcloset.network.BoardData
import kotlinx.android.synthetic.main.activity_http_test.*
import kotlinx.android.synthetic.main.addclothes.view.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.register.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class Register: AppCompatActivity() {
    val TAG: String = "Register"
    var isExistBlank = false
    var isPWSame = false
    var isBirthdayright = false
    var isEmailChecked = false

    lateinit var gen: String
    var t_stringBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val myadapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item)

        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gender_spinner.adapter = myadapter
        gender_spinner.setSelection(0)
        gender_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                gen = (view as? TextView)?.text.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        check_register.setOnClickListener {
            thread{
                var email = id_register.text.toString()
                if(email.isEmpty()){
                    runOnUiThread {
                        Toast.makeText(this, "회원가입 실패 \n 관리자에게 문의하세요요", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    var jsonobj = JSONObject()
                    jsonobj.put("email",email)

                    // 장고 이메일체크 url - 나중에 수정
                    val url = "http://172.30.1.44:8000/register/emailcheck"

                    //Okhttp3라이브러리의 OkHttpClient객체를 이요해서 작업
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
                    var result2 = result!!.split(':')
                    Log.d("http", result2[1])
                    Log.d("http",result!!)
                    //로그인 성공여부가 메시지로 전달되면 그에 따라 다르게 작업할 수 있도록 코드변경하기
                    if(result2[1]=="okay"){
                        isEmailChecked = true
                        // 사용 가능 이메일 토스트 메세지 띄우기
                        runOnUiThread {
                            Toast.makeText(this, "사용 가능한 이메일입니다.", Toast.LENGTH_LONG).show()
                        }
                    } else if(result2[1]=="fail") {
                        isEmailChecked = false
                        // 중복이메일 토스트 메세지 띄우기
                        runOnUiThread {
                            Toast.makeText(this, "중복된 이메일입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        submit_register.setOnClickListener {
            thread {
                var email = id_register.text.toString()
                var pw = pw_register.text.toString()
                var pwcheck = pwcheck_register.text.toString()
                var name = name_register.text.toString()
                var birthday = birthday_register.text.toString()

                if(t_stringBuilder.isNotEmpty()) {
                    t_stringBuilder.delete(0, t_stringBuilder.toString().length)
                }
                //db테이블에 맞게 입력 받은 생년월일 형식 변환
                t_stringBuilder.append(birthday)
                t_stringBuilder.insert(4,'-')
                t_stringBuilder.insert(7,'-')
                birthday = t_stringBuilder.toString()

                // 유저가 항목을 다 채우지 않았을 경우
                if (email.isEmpty() || pw.isEmpty() || pwcheck.isEmpty() || name.isEmpty() || birthday.isEmpty() || gen =="") {
                    isExistBlank = true
                } else {
                    isExistBlank = false
                }
                //비밀번호와 비밀번호 확인이 일치하는지
                if (pw == pwcheck) {
                    isPWSame = true
                } else {
                    isPWSame = false
                }
                if (birthday.length == 10) { //생일이 8자리 맞게 입력되었는지 - '-'두개 포함해서 10
                    isBirthdayright = true
                } else {
                    isBirthdayright = false
                }
                Log.d("test", "$birthday, $isBirthdayright")

                if (!isExistBlank && isPWSame && isBirthdayright && isEmailChecked) {
                    //서버로 전송할 JSONObject 만들기 - 사용자가 입력한 정보를 담고 있음
                    var jsonobj = JSONObject()
                    jsonobj.put("email",email)
                    jsonobj.put("pw",pw)
                    jsonobj.put("pwcheck",pwcheck)
                    jsonobj.put("name",name)
                    jsonobj.put("birth",birthday)
                    if(gen=="남자"){
                      jsonobj.put("gender",1)
                    } else if(gen=="여자") {
                      jsonobj.put("gender",2)
                    }
                    // 장고 회원가입 페이지 url
                    val url = "http://172.30.1.44:8000/register/"

                    //Okhttp3라이브러리의 OkHttpClient객체를 이요해서 작업
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
                    var result3 = result!!.split(':')
                    Log.d("http",result!!)
                    //로그인 성공여부가 메시지로 전달되면 그에 따라 다르게 작업할 수 있도록 코드변경하기
                    if(result3[1]=="okay"){
                        // 회원가입 성공 토스트 메세지 띄우기
                        runOnUiThread {
                            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_LONG).show()
                        }
                        // 로그인 화면으로 이동
                        val intent = Intent(this, FirstLogin::class.java)
                        startActivity(intent)
                    } else if(result3[1]=="fail") {
                        // 로그인 성공 토스트 메세지 띄우기
                        runOnUiThread {
                            Toast.makeText(this, "회원가입 실패 \n 관리자에게 문의하세요", Toast.LENGTH_SHORT).show()
                        }
                    }


                } else {

                    // 상태에 따라 다른 다이얼로그 띄워주기
                    if (isExistBlank) {   // 작성 안한 항목이 있을 경우
                        runOnUiThread {
                            dialog("blank")
                        }
                    } else if (!isPWSame) { // 입력한 비밀번호가 다를 경우
                        runOnUiThread {
                            dialog("not same")
                        }
                    } else if (!isBirthdayright) {
                        runOnUiThread {
                            dialog("wrong birthday")
                        }
                    }else if (!isEmailChecked) {
                        runOnUiThread {
                            dialog("wrong email")
                        }
                    }
                }
            }
        }
        back_register.setOnClickListener{
            val intent = Intent(this, FirstLogin::class.java).apply {
            }
            startActivity(intent)
        }
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
        //중복된 이메일일 경우
        else if(type.equals("wrong email")){
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("사용 불가능한 이메일입니다.")
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



}
