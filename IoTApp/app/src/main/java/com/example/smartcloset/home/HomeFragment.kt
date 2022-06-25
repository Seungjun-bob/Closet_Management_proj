package com.example.smartcloset.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Point
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.MainActivity
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.home.view.*

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartcloset.compare.RecyclerAdapter
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Response
import java.util.*
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.smartcloset.auth_cnt
import com.example.smartcloset.login.userId
import kotlinx.android.synthetic.main.compare.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import com.example.smartcloset.login.userId


class HomeFragment : Fragment() {

    val PERMISSION_LOCATION = 10
    lateinit var mainActivity: MainActivity
    private var curPoint : Point? = null
    var datalist =ArrayList<Int>()

    companion object {
        fun newInstance() = HomeFragment()
    }

    lateinit var weatherRecyclerView : RecyclerView
    lateinit var rcmdClothRecyclerView : RecyclerView

    private var base_date = "20210510"  // 발표 일자
    private var base_time = "1400"      // 발표 시각
    private var nx = 55              // 예보지점 X 좌표
    private var ny = 127            // 예보지점 Y 좌표



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home, container, false)
        requirePermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_LOCATION)
        requirePermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_LOCATION)


        return view
    }
    //
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        sendImgName()

        weatherRecyclerView = view.weatherRecyclerView
        rcmdClothRecyclerView = view.recommendation_recyclerView

        // 리사이클러 뷰 매니저 설정
        weatherRecyclerView.layoutManager = LinearLayoutManager(mainActivity).also { it.orientation = LinearLayoutManager.HORIZONTAL }
        rcmdClothRecyclerView.layoutManager = LinearLayoutManager(mainActivity).also { it.orientation = LinearLayoutManager.HORIZONTAL }

        //RecyclerView 선언
//        var clothRecyclerView:RecyclerView? = getView()?.findViewById(R.id.home_recycler)

        for(i in 0..7){
            //비교할 옷 사진 데이터들을 받아와 표시할 곳
            datalist.add(R.drawable.p1)
        }

        val rcmdClothAdapter = ClothAdapter(mainActivity, R.layout.home_item, datalist)


        rcmdClothRecyclerView.adapter = rcmdClothAdapter


        // 내 위치 위경도 가져와서 날씨 정보 설정하기
        requestLocation()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity().applicationContext)

        if (ActivityCompat.checkSelfPermission(
                mainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun setWeather(nx: Int, ny: Int) {
        // 준비 단계 : base_date(발표 일자), base_time(발표 시각)
        // 현재 날짜, 시간 정보 가져오기
        val cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA)
        base_date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time) // 현재 날짜
        val timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time) // 현재 시각
        val timeM = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time) // 현재 분
        // API 가져오기 적당하게 변환
        base_time = Common().getBaseTime(timeH, timeM)
        // 현재 시각이 00시이고 45분 이하여서 baseTime이 2330이면 어제 정보 받아오기
        if (timeH == "00" && base_time == "2330") {
            cal.add(Calendar.DATE, -1).toString()
            base_date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        }
        Log.d("test","$base_date (${base_date.length} , $base_time(${base_time.length}) $nx , $ny")
        // 날씨 정보 가져오기
        // (한 페이지 결과 수 = 60, 페이지 번호 = 1, 응답 자료 형식-"JSON", 발표 날싸, 발표 시각, 예보지점 좌표)
        val call = ApiObject.retrofitService.getWeather(60, 1, "JSON", base_date, base_time, nx, ny)
//        val call = ApiObject.retrofitService.getWeather(60, 1, "JSON", "20220614", "0600", nx, ny)
                // 비동기적으로 실행하기
        call.enqueue(object : retrofit2.Callback<WEATHER> {
            // 응답 성공 시
            override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {
                if (response.isSuccessful) {
                    // 날씨 정보 가져오기
                    val it: List<ITEM> = response.body()!!.response.body.items.item
                    Log.d("test","${it[0].toString()}")
                    // 현재 시각부터 1시간 뒤의 날씨 6개를 담을 배열
                    val weatherArr = arrayOf(ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather())

                    // 배열 채우기
                    var index = 0
                    val totalCount = response.body()!!.response.body.totalCount - 1
                    Log.d("test", response.body().toString())
                    for (i in 0..totalCount) {
                        index %= 6
                        when(it[i].category) {

                            "PTY" -> weatherArr[index].rainType = it[i].fcstValue   // 강수 형태
                            "REH" -> weatherArr[index].humidity = it[i].fcstValue     // 습도
                            "SKY" -> weatherArr[index].sky = it[i].fcstValue          // 하늘 상태
                            "T1H" -> weatherArr[index].temp = it[i].fcstValue        // 기온
                            else -> continue
                        }
                        index++
                    }
                    // 각 날짜 배열 시간 설정
                    for (i in 0..5) weatherArr[i].fcstTime = it[i].fcstTime

                    // 리사이클러 뷰에 데이터 연결
                    weatherRecyclerView.adapter = WeatherAdapter(weatherArr)

                    // 토스트 띄우기
                    Toast.makeText(mainActivity, it[0].fcstDate + ", " + it[0].fcstTime + "의 날씨 정보입니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WEATHER>, t: Throwable) {

            }
//
//             응답 실패 시
//            override fun onFailure(call: Call<WEATHER>, t: Throwable) {
//                val tvError = findViewById<TextView>(R.id.tvError)
//                tvError.text = "api fail : " +  t.message.toString() + "\n 다시 시도해주세요."
//                tvError.visibility = View.VISIBLE
//                Log.d("api fail", t.message.toString())
//            }
        })
    }


//     내 현재 위치의 위경도를 격자 좌표로 변환하여 해당 위치의 날씨정보 설정하기
    private fun requestLocation() {
        val locationClient = LocationServices.getFusedLocationProviderClient(mainActivity)

        try {
            // 나의 현재 위치 요청
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 60 * 1000    // 요청 간격(1초)
            }
            val locationCallback = object : LocationCallback() {
                // 요청 결과
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onLocationResult(p0: LocationResult?) {
                    p0?.let {
                        for (location in it.locations) {
                            // 현재 위치의 위경도를 격자 좌표로 변환
                            curPoint = Common().dfsXyConv(location.latitude, location.longitude)
                            Log.d("kim","${location.latitude} ========== ${location.longitude}")
                            // 오늘 날짜 텍스트뷰 설정
                            tvDate?.text = SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().time) + "날씨"
                            // nx, ny지점의 날씨 가져와서 설정하기
                            Log.d("kim","${curPoint!!.x} ===== ${curPoint!!.y}")
                            setWeather(55, 127)
                        }
                    }
                }
            }

            // 내 위치 실시간으로 감지
            locationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        } catch (e : SecurityException) {
            e.printStackTrace()
        }
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
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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
            PERMISSION_LOCATION -> {
                if(auth_cnt == 0 ) { // 처음 실행 한번만 뜨도록
                    Toast.makeText(mainActivity, "승인", Toast.LENGTH_LONG).show()
                    auth_cnt = auth_cnt + 1
                }
            }
        }
    }

    private fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERMISSION_LOCATION -> Toast.makeText(
                mainActivity,
                "위치 권한을 승인해야 사용할 수 있습니다.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    fun sendImgName(){
//        Toast.makeText(mainActivity, "제대로 전송됨", Toast.LENGTH_LONG).show()
        thread{

            //이미지 이름을 url 뒤에 붙여 전달해줌
            var jsonobj = JSONObject()
            Log.d("bit_img_img", "이미지 이름 전송함")
            val url = "http://172.30.1.53:8000/recommend/rcmd/?id=" + userId +"/"  //장고 서버 주소..? 랑 뭘 넣어야하지? view 함수에 들어갈 ~

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
            val response: okhttp3.Response = client.newCall(myrequest).execute()

            //response에서 메시지꺼내서 로그 출력하기 -> 결과가 뭘로 오는지, 이미지 이름과 카테고리 분류된 결과가 오면 DB에 저장하는 코드 작성
            //결과를 받아와서 모델 객체를.. 만들어서? recycler View에 반영해줘야 함
            val result:String? = response.body()?.string()

            Log.d("http",result!!) //로그 찍어본 후에 파싱해서 옷 객체로 만들고, 리사이클러뷰에 띄우기

            //여기서 데이터 파싱 후 옷 모델 만들어주기? 해야함

            //옷 모델을 만들어서 리사이클러뷰에 넣어줘야함 (= 배열로 만들어서?)
//            loadImage("https://closetimg103341-dev.s3.us-west-2.amazonaws.com/test5.png")
            Log.d("bit_img_img", "여기까지 넘어옴")
            mainActivity.runOnUiThread {
                //여기서 리사이클러뷰를 바꿔줘야 하나?
                Log.d("bit_img_img", "여기까지 넘어옴")

            }

        }
    }
    fun loadImage(imageUrl:String){
        thread{
            val url = URL(imageUrl)
            val con = url.openConnection() as HttpURLConnection
            var image = con.inputStream
            var imagedata = image.readBytes()
            var bitmap = BitmapFactory.decodeByteArray(imagedata,0,imagedata.size)

            mainActivity.runOnUiThread{
                img_compare_preview.setImageBitmap(bitmap)
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