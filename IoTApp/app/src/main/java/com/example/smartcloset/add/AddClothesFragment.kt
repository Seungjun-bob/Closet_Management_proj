package com.example.smartcloset.add

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread
import com.example.smartcloset.R
import com.example.smartcloset.MainActivity
import com.example.smartcloset.home.HomeFragment
import com.example.smartcloset.login.FirstLogin
import com.example.smartcloset.login.userId

import com.example.smartcloset.network.MyMqtt
import kotlinx.android.synthetic.main.addclothes.*
import kotlinx.android.synthetic.main.addclothes.view.*
import kotlinx.android.synthetic.main.register.*
import okhttp3.*
import org.json.JSONObject
import kotlin.concurrent.thread

class AddClothesFragment: Fragment() {
    //카메라/앨범 관련 권한
    val PERMISSION_Album = 101 // 앨범 권한 처리
    val REQUEST_STORAGE = 1

    val PERMISSION_CAMERA = 1001 //맞나?
    val REQUEST_CAMERA = 2 //맞나?

    lateinit var realUri: Uri
    lateinit var tag1data:String
    lateinit var tag2data:String
    lateinit var tag3data:String

    //옷 저장 통신용 변수
    var t_stringBuilder = StringBuilder()

    //반환값(카테고리/색상)을 저장할 변수
    var analyze_category:String = ""
    var analyze_color:String = ""
    // 스피너 선택값을 저장한 변수
    lateinit var clothes_category:String
    lateinit var clothes_color:String
    var isExistBlank = true

    var save_success = false

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    var datalist =ArrayList<Int>()
    lateinit var mainActivity: MainActivity

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewF = inflater.inflate(R.layout.addclothes, container, false)
        // 카테고리/색상 수동 선택
        // 1번 스피너 - 옷 종류
        val myadapter1 = ArrayAdapter.createFromResource(mainActivity, R.array.type, android.R.layout.simple_spinner_item)

        myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewF.tag1.adapter = myadapter1
        viewF.tag1.setSelection(0)

        viewF.tag1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tag1data = (view as? TextView)?.text.toString()
                viewF.tag2.setSelection(0)
                viewF.tag3.setSelection(0)
                when(tag1data){
                    "" -> {
                        //1번 스피너 공백일 때 -> 2번 스피너 선택지 없음
                        val myadapter2 = ArrayAdapter.createFromResource(mainActivity, R.array.tagnone, android.R.layout.simple_spinner_item)
                        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        viewF.tag2.adapter = myadapter2
                        viewF.tag2.setSelection(0)

                        viewF.tag2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                tag2data = (view as? TextView)?.text.toString()

                                // 세번째 태그 오픈
                                val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagnone, android.R.layout.simple_spinner_item)
                                myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                viewF.tag3.adapter = myadapter3
                                viewF.tag3.setSelection(0)

                                viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                        tag3data = (view as? TextView)?.text.toString()
                                        clothes_color = ""
                                        clothes_category = ""
                                    }
                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                    }
                                }
                            }
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }
                        }
                    }
                    // 1번 스피너 값이 상의인 경우
                    "상의" -> {
                        //두번째 스피너 오픈 - 상의 스피너
                        val myadapter2 = ArrayAdapter.createFromResource(mainActivity, R.array.top, android.R.layout.simple_spinner_item)
                        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        viewF.tag2.adapter = myadapter2
                        viewF.tag2.setSelection(0)

                        viewF.tag2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                    tag2data = (view as? TextView)?.text.toString()

                                    when (tag2data) {
                                        "" -> {
                                            //2번 스피너 공백일 때 -> 3번 스피너 선택지 없음
                                            val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagnone, android.R.layout.simple_spinner_item)
                                            myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            viewF.tag3.adapter = myadapter3
                                            viewF.tag3.setSelection(0)

                                            viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                    tag3data = (view as? TextView)?.text.toString()
                                                    clothes_color = ""
                                                    clothes_category = ""
                                                }
                                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                                }
                                            }
                                        }
                                        else -> {
                                            when (tag2data) {
                                                "반팔 상의" -> {
                                                    clothes_category = "short_sleeve_top"
                                                }
                                                "긴팔 상의" -> {
                                                    clothes_category = "long_sleeve_top"
                                                }
                                                "반팔 아우터" -> {
                                                    clothes_category = "short_sleeve_outer"
                                                }
                                                "긴팔 아우터" -> {
                                                    clothes_category = "short_sleeve_outer"
                                                }
                                                "조끼(민소매)" -> {
                                                    clothes_category = "vest"
                                                }
                                                "나시" -> {
                                                    clothes_category = "sling"
                                                }
                                            }

                                            // 세번째 태그 오픈 - 색상
                                            val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.clothes_colors, android.R.layout.simple_spinner_item)
                                            myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            viewF.tag3.adapter = myadapter3
                                            viewF.tag3.setSelection(0)

                                            viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                    tag3data = (view as? TextView)?.text.toString()
                                                    when(tag3data){
                                                        "" -> {
                                                            clothes_color = ""
                                                        }
                                                        "검정" -> {
                                                            clothes_color = "black"
                                                        }
                                                        "블루" -> {
                                                            clothes_color = "blue"
                                                        }
                                                        "레드" -> {
                                                            clothes_color = "red"
                                                        }
                                                        "그린" -> {
                                                            clothes_color = "green"
                                                        }
                                                        "화이트" -> {
                                                            clothes_color = "white"
                                                        }
                                                        "그레이" -> {
                                                            clothes_color = "gray"
                                                        }
                                                        "베이지" -> {
                                                            clothes_color = "beige"
                                                        }
                                                        "패턴" -> {
                                                            clothes_color = "pattern"
                                                        }
                                                    }
                                                }
                                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                                }
                                            }
                                        }
                                    }
                                }
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                }
                            }
                    }
                    // 1번 스피너 값이 하의인 경우
                    "하의" -> {
                        //두번째 스피너 오픈 - 하의 스피너
                        val myadapter2 = ArrayAdapter.createFromResource(mainActivity, R.array.bottom,android.R.layout.simple_spinner_item)
                        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        viewF.tag2.adapter = myadapter2
                        viewF.tag2.setSelection(0)

                        viewF.tag2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                    tag2data = (view as? TextView)?.text.toString()

                                    when (tag2data) {
                                        "" -> {
                                            //2번 스피너 공백일 때 -> 3번 스피너 선택지 없음
                                            val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagnone, android.R.layout.simple_spinner_item)
                                            myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            viewF.tag3.adapter = myadapter3
                                            viewF.tag3.setSelection(0)

                                            viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                                                    tag3data = (view as? TextView)?.text.toString()
                                                    clothes_color = ""
                                                    clothes_category = ""
                                                }
                                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                                }
                                            }
                                        }
                                        else -> {
                                            when (tag2data) {
                                                "반바지" -> {
                                                    clothes_category = "shorts"
                                                }
                                                "긴바지" -> {
                                                    clothes_category = "trousers"
                                                }
                                                "치마" -> {
                                                    clothes_category = "skirt"
                                                }

                                            }
                                            // 세번째 태그 오픈
                                            val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.clothes_colors, android.R.layout.simple_spinner_item)
                                            myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            viewF.tag3.adapter = myadapter3
                                            viewF.tag3.setSelection(0)

                                            viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                                                        tag3data = (view as? TextView)?.text.toString()
                                                        when(tag3data){
                                                            "" -> {
                                                                clothes_color = ""
                                                            }
                                                            "검정" -> {
                                                                clothes_color = "black"
                                                            }
                                                            "블루" -> {
                                                                clothes_color = "blue"
                                                            }
                                                            "레드" -> {
                                                                clothes_color = "red"
                                                            }
                                                            "그린" -> {
                                                                clothes_color = "green"
                                                            }
                                                            "화이트" -> {
                                                                clothes_color = "white"
                                                            }
                                                            "그레이" -> {
                                                                clothes_color = "gray"
                                                            }
                                                            "베이지" -> {
                                                                clothes_color = "beige"
                                                            }
                                                            "패턴" -> {
                                                                clothes_color = "pattern"
                                                            }
                                                        }
                                                    }
                                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                                    }
                                                }
                                        }
                                    }
                                }
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                }
                        }
                    }
                    // 1번 스피너 값이 원피스인 경우
                    "원피스" -> {
                        val myadapter2 = ArrayAdapter.createFromResource(mainActivity, R.array.dress,android.R.layout.simple_spinner_item)
                        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        viewF.tag2.adapter = myadapter2
                        viewF.tag2.setSelection(0)

                        viewF.tag2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                tag2data = (view as? TextView)?.text.toString()
                                when (tag2data) {
                                    "" -> {
                                        //2번 스피너 공백일 때 -> 3번 스피너 선택지 없음
                                        val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagnone, android.R.layout.simple_spinner_item)
                                        myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                        viewF.tag3.adapter = myadapter3
                                        viewF.tag3.setSelection(0)

                                        viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                tag3data = (view as? TextView)?.text.toString()
                                                clothes_color = ""
                                                clothes_category = ""
                                            }
                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                            }
                                        }

                                    }
                                    else -> {
                                        when (tag2data) {
                                            "반팔 원피스" -> {
                                                clothes_category = "short_sleeve_dress"
                                            }
                                            "긴팔 원피스" -> {
                                                clothes_category = "long_sleeve_dress"
                                            }
                                            "민소매 원피스" -> {
                                                clothes_category = "vest_dress"
                                            }
                                            "나시 원피스" -> {
                                                clothes_category = "sling_dress"
                                            }

                                        }
                                        // 세번째 태그 오픈
                                        val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.clothes_colors, android.R.layout.simple_spinner_item)
                                        myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                        viewF.tag3.adapter = myadapter3
                                        viewF.tag3.setSelection(0)

                                        viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                tag3data = (view as? TextView)?.text.toString()
                                                when(tag3data){
                                                    "" -> {
                                                        clothes_color = ""
                                                    }
                                                    "검정" -> {
                                                        clothes_color = "black"
                                                    }
                                                    "블루" -> {
                                                        clothes_color = "blue"
                                                    }
                                                    "레드" -> {
                                                        clothes_color = "red"
                                                    }
                                                    "그린" -> {
                                                        clothes_color = "green"
                                                    }
                                                    "화이트" -> {
                                                        clothes_color = "white"
                                                    }
                                                    "그레이" -> {
                                                        clothes_color = "gray"
                                                    }
                                                    "베이지" -> {
                                                        clothes_color = "beige"
                                                    }
                                                    "패턴" -> {
                                                        clothes_color = "pattern"
                                                    }
                                                }
                                            }
                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                            }
                                        }
                                    }
                                }
                            }
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }
                        }
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
//                result.text = "선택된 가수가 없습니다."
            }
        }
        //************AI 모델 반환값을 기준으로 카테고리 대분류 선택************
        when(analyze_category){
            // 상의
            "short_sleeve_top" -> {
                viewF.tag1.setSelection(1)
                viewF.tag2.setSelection(1)
            }
            "long_sleeve_top" -> {
                viewF.tag1.setSelection(1)
                viewF.tag2.setSelection(2)
            }
            "short_sleeve_outer" -> {
                viewF.tag1.setSelection(1)
                viewF.tag2.setSelection(3)
            }
            "long_sleeve_outer" -> {
                viewF.tag1.setSelection(1)
                viewF.tag2.setSelection(4)
            }
            "vest" -> {
                viewF.tag1.setSelection(1)
                viewF.tag2.setSelection(5)
            }
            "sling" -> {
                viewF.tag1.setSelection(1)
                viewF.tag2.setSelection(6)
            }
            // 하의
            "shorts"-> {
                viewF.tag1.setSelection(2)
                viewF.tag2.setSelection(1)
            }
            "trousers"-> {
                viewF.tag1.setSelection(2)
                viewF.tag2.setSelection(2)
            }
            "skirt" -> {
                viewF.tag1.setSelection(2)
                viewF.tag2.setSelection(3)
            }
            // 원피스
            "short_sleeve_dress"-> {
                viewF.tag1.setSelection(3)
                viewF.tag2.setSelection(1)
            }
            "long_sleeve_dress"-> {
                viewF.tag1.setSelection(3)
                viewF.tag2.setSelection(2)
            }
            "vest_dress"-> {
                viewF.tag1.setSelection(3)
                viewF.tag2.setSelection(3)
            }
            "sling_dress"-> {
                viewF.tag1.setSelection(3)
                viewF.tag2.setSelection(4)
            }
        }
        when(analyze_color){
            "black"-> {
                viewF.tag3.setSelection(1)
            }
            "blue"-> {
                viewF.tag3.setSelection(2)
            }
            "red"-> {
                viewF.tag3.setSelection(3)
            }
            "green"-> {
                viewF.tag3.setSelection(4)
            }
            "white"-> {
                viewF.tag3.setSelection(5)
            }
            "gray"-> {
                viewF.tag3.setSelection(6)
            }
            "beige"-> {
                viewF.tag3.setSelection(7)
            }
            "pattern"-> {
                viewF.tag3.setSelection(8)
            }
        }






        // 앨범 버튼 클릭 리스너 구현
        viewF.album_addclothes.setOnClickListener{
            requirePermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_Album)
        }

        // 카메 라 버튼 클릭 리스너 구현
        viewF.camera_addclothes.setOnClickListener(View.OnClickListener {
            requirePermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA)

        })

        viewF.cancel_addclothes.setOnClickListener{
            //취소 코드 추가
            mainActivity.changeFragment(1)
            Toast.makeText(mainActivity,"등록이 취소되었습니다", Toast.LENGTH_SHORT).show()
        }
        // 등록 버튼 누르면 http 통신으로 서버에 전달-db저장
        viewF.save_addclothes.setOnClickListener{
            thread {
                var buydate = buydate.text.toString()
                if(t_stringBuilder.isNotEmpty()) {
                    t_stringBuilder.delete(0, t_stringBuilder.toString().length)
                }
                //db테이블에 맞게 입력 받은 생년월일 형식 변환
                t_stringBuilder.append(buydate)
                if(t_stringBuilder.toString().length==8) {
                    t_stringBuilder.insert(4, '-')
                    t_stringBuilder.insert(7, '-')
                    buydate = t_stringBuilder.toString()
                }
                //저장할 최종 카테고리/색상을 담을 변수
                var final_category = clothes_category
                var final_color = clothes_color
                // 저장할 카테고리가 선택되어있는지
                if(final_category.isEmpty() || final_color.isEmpty() || buydate.isEmpty()){
                    isExistBlank = true
                } else {
                    isExistBlank = false
                }
                if(!isExistBlank){
                    //서버로 전송할 JSONObject 만들기 - 카테고리 정보를 담고 있음
                    var jsonobj = JSONObject()
                    jsonobj.put("id",userId) // 어떤 유저의 등록인지 유저id값 포함
                    jsonobj.put("buydate",buydate)
                    jsonobj.put("myColor",final_category)
                    jsonobj.put("myCategory",final_color)

                    // 장고 등록 페이지 url - 나중에 수정
                    val url = "http://172.30.1.22:8000/register/"

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
                    var save_result = result!!.split(':')
                    Log.d("http",result!!)
                    // 성공여부가 메시지로 전달되면 그에 따라 다르게 작업할 수 있도록 코드변경하기
                    if(save_result[1]=="okay"){
                        // 등록 성공 토스트 메세지 띄우기
                        runOnUiThread {
                            Toast.makeText(mainActivity, "등록 성공", Toast.LENGTH_LONG).show()
                        }
                        save_success = true

                    } else if(save_result[1]=="fail") {
                        // 등록 성공 토스트 메세지 띄우기
                        runOnUiThread {
                            Toast.makeText(mainActivity, "등록 실패 \n 관리자에게 문의하세요", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {

                }
            }
            if(save_success) {
                // 저장이 끝나면 홈화면으로 돌아가기
                mainActivity.changeFragment(1)
            }

        }
        return viewF
    }













    /**자식 액티비티에서 권한 요청 시 직접 호출하는 메서드
     * @param permissions 권한 처리를 할 권한 목록
     * @param requestCode 권한을 요청한 주체가 어떤 것인지 구분하기 위함.
     * */
    @RequiresApi(Build.VERSION_CODES.N)
    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
//        Logger.d("권한 요청")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            // isAllPermissionsGranted : 권한이 모두 승인 되었는지 여부 저장
            // all 메서드를 사용하면 배열 속에 들어 있는 모든 값을 체크할 수 있다.
            val isAllPermissionsGranted =
                permissions.all { ActivityCompat.checkSelfPermission(mainActivity,it) == PackageManager.PERMISSION_GRANTED }
            if (isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                // 사용자에 권한 승인 요청
                ActivityCompat.requestPermissions(mainActivity, permissions, requestCode)
            }
        }
    }
    /** 사용자가 권한을 승인하거나 거부한 다음에 호출되는 메서드
     * @param requestCode 요청한 주체를 확인하는 코드
     * @param permissions 요청한 권한 목록
     * @param grantResults 권한 목록에 대한 승인/미승인 값, 권한 목록의 개수와 같은 수의 결괏값이 전달된다.
     * */
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun permissionGranted(requestCode: Int) {
        when (requestCode) {
            PERMISSION_CAMERA -> openCamera()
            PERMISSION_Album -> openGallery()
        }
    }

    private fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERMISSION_CAMERA -> Toast.makeText(
                mainActivity,
                "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.",
                Toast.LENGTH_LONG
            ).show()

            PERMISSION_Album -> Toast.makeText(
                mainActivity,
                "저장소 권한을 승인해야 앨범에서 이미지를 불러올 수 있습니다.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQUEST_STORAGE)

    }



    //***************카메라
    @RequiresApi(Build.VERSION_CODES.N)
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        createImageUri(newFileName(), "image/bmp")?.let { uri ->
            realUri = uri // var 맞나?
            // MediaStore.EXTRA_OUTPUT을 Key로 하여 Uri를 넘겨주면
            // 일반적인 Camera App은 이를 받아 내가 지정한 경로에 사진을 찍어서 저장시킨다.
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
            startActivityForResult(intent, REQUEST_CAMERA)
//******************************************************************************************
            // 찍은 사진을 AI모델에 보내서 1차 분석 카테고리/색상 받아오기
            // 어떻게 보냄?
//            thread {
//                var ready = "ready"
//                var jsonobj = JSONObject()
//                jsonobj.put("ready",ready)
//
//                // 장고 AI모델 페이지 url? - 나중에 수정
//                val url = "http://172.30.1.22:8000/register/"
//
//                //Okhttp3라이브러리의 OkHttpClient객체를 이요해서 작업
//                val client = OkHttpClient()
//
//                //json데이터를 이용해서 request 처리
//                val jsondata = jsonobj.toString()
//                //서버에 요청을 담당하는 객체
//                val builder = Request.Builder()    // request객체를 만들어주는 객체 생성
//                builder.url(url)                   //Builder객체에 request할 주소(네트워크상의 주소)셋팅
//                builder.post(RequestBody.create(MediaType.parse("application/json"),jsondata)) // 요청메시지 만들고 요청메시지의 타입이 json이라고 설정
//                val myrequest: Request = builder.build() //Builder객체를 이용해서 request객체 만들기
//                //생성한 request 객체를 이용해서 웹에 request하기 - request결과로 response 객체가 리턴
//                val response: Response = client.newCall(myrequest).execute()
//
//                //response에서 메시지꺼내서 로그 출력하기
//                val result:String? = response.body()?.string()
//                var analyze_result = result!!.split('\"')
//                Log.d("http",result!!)
//                //로그인 성공여부가 메시지로 전달되면 그에 따라 다르게 작업할 수 있도록 코드변경하기
//                analyze_category = analyze_result[3]
//                analyze_color = analyze_result[7]
//            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun newFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "$filename.bmp"
    }

    private fun createImageUri(filename: String, mimeType: String): Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return mainActivity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }




    //******************실행
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    realUri?.let { uri ->
                        imagePreview.setImageURI(uri)
                    }
                }
                REQUEST_STORAGE -> {
                    data?.data?.let { uri ->
                        imagePreview.setImageURI(uri)
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Fragment에선 Activity에서 사용하는 메소드들을 사용할 수 없기 때문에 onAttach(프레그먼트가 액티비티에 붙여지는 생명주기)에서
        //Context를 받아와 MainActivity로 캐스팅해주면 메소드들을 사용할 수 있다.
        //여기에서는 Adapter를 생성할 때 context를 넘겨주기 위해 이 방법을 사용했다.
        mainActivity = context as MainActivity

    }

}