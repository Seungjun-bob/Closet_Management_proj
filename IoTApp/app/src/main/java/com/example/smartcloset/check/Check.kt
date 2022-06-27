package com.example.smartcloset.check

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.smartcloset.login.userId
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.MainActivity
import com.example.smartcloset.R
import okhttp3.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class Check : Fragment() {
    lateinit var mainActivity: MainActivity

    var clothlist = ArrayList<ClothData>()
    lateinit var checkAdapter:CheckAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.checkclothes, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //여기서 하는게 맞나
        checkAdapter = CheckAdapter(context as MainActivity, R.layout.cloth_item, clothlist)
        checkAll()

        //RecyclerView 선언
        var checkRecyclerView: RecyclerView? = getView()?.findViewById(R.id.result_grid)
        checkRecyclerView!!.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))

        //Adapter 생성하고 연결해주기

        checkRecyclerView?.adapter = checkAdapter


        val lm = GridLayoutManager(context, 3)
        checkRecyclerView?.layoutManager = lm
        checkRecyclerView?.setHasFixedSize(true)


        checkAdapter.setItemClickListener(object : CheckAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val item = clothlist[position]
                val dialog = ClothDetailDialog(mainActivity)
                dialog.showdetailDia(item)
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    fun checkAll(){
        thread{
            //이미지 이름을 url 뒤에 붙여 전달해줌
//            var jsonobj = JSONObject()
//            jsonobj.put("","" )
            val url = "http://192.168.0.10:8000/cloth/check/"+ userId+"/"

            //Okhttp3라이브러리의 OkHttpClient객체를 이용해서 작업
            val client = OkHttpClient()
//            //json데이터를 이용해서 request 처리
//            val jsondata = jsonobj.toString()
            //서버에 요청을 담당하는 객체
            val builder = Request.Builder()    // request객체를 만들어주는 객체 생성
            builder.url(url) //Builder객체에 request할 주소(네트워크상의 주소)셋팅
            builder.get()
//            builder.post(RequestBody.create(MediaType.parse("application/json"),jsondata)) // 요청메시지 만들고 요청메시지의 타입이 json이라고 설정
            val myrequest: Request = builder.build() //Builder객체를 이용해서 request객체 만들기
            //생성한 request 객체를 이용해서 웹에 request하기 - request결과로 response 객체가 리턴
            // ==> Response가 서버에서 돌려준 josn 객체인가??
            val response: Response = client.newCall(myrequest).execute()

            //response에서 메시지꺼내서 로그 출력하기 -> 결과가 뭘로 오는지, 이미지 이름과 카테고리 분류된 결과가 오면 DB에 저장하는 코드 작성
            //결과를 받아와서 모델 객체를.. 만들어서? recycler View에 반영해줘야 함
            val result:String? = response.body()?.string()

            Log.d("http",result!!) //로그 찍어본 후에 파싱해서 옷 객체로 만들고, 리사이클러뷰에 띄우기

            //옷 모델을 만들어서 리사이클러뷰에 넣어줘야함 (= 배열로 만들어서?)
            Log.d("bit_img_img", "여기까지 넘어옴")

            val jsonArray = JSONArray(result.toString())

            for(i in 1 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val imgname = "https://group8img.s3.us-west-2.amazonaws.com/"+jsonObject.getString("myimg")+".jpg"
                val imgurl = URL(imgname)
                val con = imgurl.openConnection() as HttpURLConnection
                val image = con.inputStream
                val imagedata = image.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(imagedata,0,imagedata.size)
//                clothlist.add(bitmap)

                val clothData = ClothData(
                    jsonObject.getString("mycategory"),
                    jsonObject.getString("mycolor"),
                    jsonObject.getString("buydate"),
                    bitmap
                )
                clothlist.add(clothData)

                mainActivity.runOnUiThread{
//                img_compare_preview.setImageBitmap(bitmap)
                    checkAdapter.notifyDataSetChanged()
                }
            }

        }
    }
}