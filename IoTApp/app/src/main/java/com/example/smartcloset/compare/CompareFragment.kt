package com.example.smartcloset.compare

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
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
import com.example.smartcloset.MainActivity
import com.example.smartcloset.R

import com.example.smartcloset.login.userId
import com.example.smartcloset.network.MyMqtt

import kotlinx.android.synthetic.main.compare.*
import kotlinx.android.synthetic.main.compare.view.*
import okhttp3.*
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.reflect.typeOf


class CompareFragment: Fragment() {
    lateinit var adapter:RecyclerAdapter
    var datalist =ArrayList<Bitmap>()
    lateinit var mainActivity: MainActivity
    val sub_topic = "iot/compare"
    //val server_uri = "tcp://52.37.48.195:1883" //broker의 ip와 port
    val server_uri = "tcp://52.37.48.195:1883" //broker의 ip와 port
    var mymqtt: MyMqtt? = null
    var uri : Uri? = null
    lateinit var mContext : Context
    val PERMISSION_CAMERA = 1001 //맞나?
    val REQUEST_CAMERA = 2 //맞나?
    var img_names = ArrayList<String>()
    lateinit var realUri:Uri
    var img_name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        mymqtt = MyMqtt(mainActivity, server_uri)
        //브로커에서 메세지 전달되면 호출될 메소드를 넘기기
        mymqtt?.mysetCallback(::onReceived) //바이트코드를 아예 참조할 수 있게 사용 ( :: )
        //브로커연결
        mymqtt?.connect(arrayOf<String>(sub_topic))

        super.onCreate(savedInstanceState)


    }

    fun onReceived(topic:String, message: MqttMessage){
        val msg = String(message.payload)
        val msgdata = msg.split(':')

        Log.d("mymqtt", msg)
    }
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

        createImageUri(newFileName(), "image/jpg")?.let { uri -> //드디어. . . 이부분이랑
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

//        여기서 호출 해서 이미지 이름을 넘겨줘야 할 거 같음
        return "$filename.jpg"   // 이 부분 바꿨고..
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
                        var bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(mainActivity.contentResolver, uri))
                        val byteArrayOutputStream :ByteArrayOutputStream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
                        val byteArray = byteArrayOutputStream.toByteArray()
                        val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)

//                        Log.d("encode_img", encoded)
                        //이미지를 서버로 보내기. 서버에서는 받은 데이터를 비트맵으로 변환해 저장
                        imgPub(encoded)
                        sendImgName(img_name)


//                        Log.d("bitmap", bitmap.toString())
//                        Log.d("tt", img_name)
//                        Log.d("uri print", realUri.toString())
//                        Toast.makeText(context, img_name, Toast.LENGTH_LONG).show()


                    }
                }
            }
        }
    }

    fun imgPub(img:String){
        mymqtt?.publish("iot/image", img+":"+img_name)
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
       // var compareRecyclerView:RecyclerView? = getView()?.findViewById(R.id.compare_recycler)
//        compare_recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))

        adapter =RecyclerAdapter(mainActivity, R.layout.compare_item, datalist, img_names)
        compare_recycler.adapter = adapter

//        view.btn_compare_exit.setOnClickListener {
//
//        }
//
//        for(i in 0..7){
//            //비교할 옷 사진 데이터들을 받아와 표시할 곳
//
//        }



//        view.btn_compare.setOnClickListener {
////            adapter.notifyDataSetChanged()
//            loadImage("https://group8img.s3.us-west-2.amazonaws.com/test3.png")
//            loadImage("https://group8img.s3.us-west-2.amazonaws.com/test4.png")
//            loadImage("https://group8img.s3.us-west-2.amazonaws.com/test5.png")
//        }



    }


    fun loadImage(imageUrl:String){
        thread{
            val url = URL(imageUrl)
            val con = url.openConnection() as HttpURLConnection
            var image = con.inputStream
            var imagedata = image.readBytes()
            var bitmap = BitmapFactory.decodeByteArray(imagedata,0,imagedata.size)

            datalist.add(bitmap)

            mainActivity.runOnUiThread{
//                img_compare_preview.setImageBitmap(bitmap)
                adapter.notifyDataSetChanged()
            }
            Log.d("klimtest","${datalist.size}")

        }
        Log.d("klimtest","end")
    }



    fun sendImgName(name:String){
        var imgs:Array<String>
        var tags:Array<String>
//        Toast.makeText(mainActivity, "제대로 전송됨", Toast.LENGTH_LONG).show()
        thread{

            //이미지 이름을 url 뒤에 붙여 전달해줌
//            var jsonobj = JSONObject()
//            jsonobj.put("img","https://closetimg103341-dev.s3.us-west-2.amazonaws.com/rank1.jpg" )//            jsonobj.put("ImgName","test_img_name" )

            Log.d("bit_img_img", "이미지 이름 전송함")
            Log.d("test", userId)
//            val url = "http://172.30.1.53:8000/recommend/compare/?id=" + userId  //장고 서버 주소..? 랑 뭘 넣어야하지? view 함수에 들어갈 ~

            val url = "http://52.37.48.195:8000/test/?img=test4&id=" + userId  //장고 서버 주소..? 랑 뭘 넣어야하지? view 함수에 들어갈 ~


            //Okhttp3라이브러리의 OkHttpClient객체를 이요해서 작업
//            val client = OkHttpClient()

            //json데이터를 이용해서 request 처리
//            val jsondata = jsonobj.toString()
//            Log.d("test", jsondata)
            //서버에 요청을 담당하는 객체
            val builder = Request.Builder()    // request객체를 만들어주는 객체 생성
            builder.url(url)                   //Builder객체에 request할 주소(네트워크상의 주소)셋팅
//            builder.post(RequestBody.create(MediaType.parse("application/json"),jsondata)) // 요청메시지 만들고 요청메시지의 타입이 json이라고 설정
            val myrequest: Request = builder.build() //Builder객체를 이용해서 request객체 만들기
            //생성한 request 객체를 이용해서 웹에 request하기 - request결과로 response 객체가 리턴
            // ==> Response가 서버에서 돌려준 josn 객체인가??
            val client = OkHttpClient.Builder()
                //.addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build()


            val response: Response = client.newCall(myrequest).execute()

            //response에서 메시지꺼내서 로그 출력하기 -> 결과가 뭘로 오는지, 이미지 이름과 카테고리 분류된 결과가 오면 DB에 저장하는 코드 작성
            //결과를 받아와서 모델 객체를.. 만들어서? recycler View에 반영해줘야 함
            val result:String? = response.body()?.string()
            Log.d("http",result!!)


            val jsonObject = JSONObject(result.trimIndent())
            val catObject = jsonObject.getString("category")
            val colorObject = jsonObject.getString("color")


            datalist.clear()
            img_names.clear()

            val compare_img = jsonObject.getString("result")
            var compare_img_list = compare_img.substringAfter("[\"")
                .substringBeforeLast("\"]").split("\",\"")

            for ( i in 0 .. (compare_img_list.size - 1) ) {
                loadImage("https://group8img.s3.us-west-2.amazonaws.com/"+ compare_img_list[i] +".jpg")
            }


            //로그 찍어본 후에 파싱해서 스플릿으로 나눈다음, 배열의 길이만큼 loadImage를 포문으로 돌리기

            //기존 리사이클러뷰에 들어갔던 데이터를 비우고

            // 받아온 결과값 이미지 url 리스트들을 for문으로 돌려 리사이클러뷰에 추가함, for 문 돌릴 때 이미지 이름도 추가
//            loadImage()


            //여기서 데이터 파싱 후 옷 모델 만들어주기? 해야함

            //옷 모델을 만들어서 리사이클러뷰에 넣어줘야함 (= 배열로 만들어서?)
//            loadImage("https://closetimg103341-dev.s3.us-west-2.amazonaws.com/test5.png")
            mainActivity.runOnUiThread {
                //여기서 리사이클러뷰를 바꿔줘야 하나?
                compare_category_tag.setText(catObject.toString())
                compare_color_tag.setText(colorObject.toString())
                Log.d("bit_img_img", "여기까지 넘어옴")


            }

        }
    }


}