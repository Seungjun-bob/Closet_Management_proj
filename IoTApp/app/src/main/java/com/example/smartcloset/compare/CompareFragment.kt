package com.example.smartcloset.compare

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.MainActivity
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.compare.*
import kotlinx.android.synthetic.main.compare.view.*
import okhttp3.*
import org.json.JSONObject
import kotlin.concurrent.thread


class CompareFragment: Fragment() {
    var uri : Uri? = null
    lateinit var mContext : Context
    val PERMISSION_CAMERA = 1001 //맞나?
    val REQUEST_CAMERA = 2 //맞나?
    lateinit var realUri:Uri
    var img_name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    var datalist =ArrayList<Int>()
    lateinit var mainActivity: MainActivity
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.compare, container, false)
        view.btn_add_comparePic.setOnClickListener {
            //등록 버튼 클릭 이벤트 리스너
                requirePermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA)

        }


        return view


    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
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
        }
    }

    private fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERMISSION_CAMERA -> Toast.makeText(
                mainActivity,
                "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //***************카메라
    @RequiresApi(Build.VERSION_CODES.N)
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        createImageUri(newFileName(), "image/jpg")?.let { uri ->
            realUri = uri // var 맞나?
            // MediaStore.EXTRA_OUTPUT을 Key로 하여 Uri를 넘겨주면
            // 일반적인 Camera App은 이를 받아 내가 지정한 경로에 사진을 찍어서 저장시킨다.
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
            startActivityForResult(intent, REQUEST_CAMERA)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun newFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        Log.d("img_name", filename)
        //여기서 이미지 이름을 http로 보내줘야 할 거 같음
        img_name = filename
//        sendImgName(img_name)
        return "$filename.jpg"
    }

    private fun createImageUri(filename: String, mimeType: String): Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return mainActivity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    //Launcher (ActivityResultLuncher로 변경해야함)
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    realUri?.let { uri ->
                        //사진이 찍히면 이미지뷰에 띄워줌, DB로 전송도 해서 값을 받아와 밑의 RecyclerViewㅇ[ 보여줘야함
                        img_compare_preview.setImageURI(uri)
                        //rest 사용해서 이미지 이름을 보내주고, 스토리지에 이미지를 저장, 이미지는 어떻게 저장?
//                        img_name = realUri.lastPathSegment.toString() //보내줄 이미지 이름

                        var bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(mainActivity.contentResolver, uri))
                        Log.d("bitmap", bitmap.toString())

                        Log.d("tt", img_name)
                        Log.d("api", realUri.toString())
                        Toast.makeText(context, img_name, Toast.LENGTH_LONG).show()


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //RecyclerView 선언
        var compareRecyclerView:RecyclerView? = getView()?.findViewById(R.id.compare_recycler)
        compareRecyclerView!!.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        for(i in 0..7){
            //비교할 옷 사진 데이터들을 받아와 표시할 곳
            datalist.add(R.drawable.p1)
        }

        //Adapter 생성하고 연결해주기
        val adapter =RecyclerAdapter(mainActivity, R.layout.compare_item,datalist)
        compareRecyclerView?.adapter = adapter


    }

    fun sendImgName(name:String){
        thread{
            //이미지 이름을 url 뒤에 붙여 전달해줌
            var jsonobj = JSONObject()
            jsonobj.put("ImgName","https://closetimg103341-dev.s3.us-west-2.amazonaws.com/$name" )
            val url = "http://192.168.200.107:8000/login"  //장고 서버 주소..? 랑 뭘 넣어야하지? view 함수에 들어갈 ~

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
            // ==> Response가 서버에서 돌려준 josn 객체인가??
            val response: Response = client.newCall(myrequest).execute()

            //response에서 메시지꺼내서 로그 출력하기 -> 결과가 뭘로 오는지, 이미지 이름과 카테고리 분류된 결과가 오면 DB에 저장하는 코드 작성
            //결과를 받아와서 모델 객체를.. 만들어서? recycler View에 반영해줘야 함
            val result:String? = response.body()?.string()

            Log.d("http",result!!) //로그 찍어본 후에 파싱해서 옷 객체로 만들고, 리사이클러뷰에 띄우기

            //여기서 데이터 파싱 후 옷 모델 만들어주기? 해야함

            //옷 모델을 만들어서 리사이클러뷰에 넣어줘야함 (= 배열로 만들어서?)

            mainActivity.runOnUiThread {
                //여기서 리사이클러뷰를 바꿔줘야 하나?
            }

        }
    }


}