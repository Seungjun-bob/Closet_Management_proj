package com.example.smartcloset.myPage

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.res.TypedArrayUtils.getString
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.mypage.*
import com.example.smartcloset.login.userId //로그인 하면서 받아온 userId전역변수
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.compare.*
import okhttp3.*
import org.json.JSONObject
import kotlin.concurrent.thread


class MyPage : AppCompatActivity() {
    private var chart: AnyChartView? = null

    private val salary: MutableList<Int> = mutableListOf()
    private val month = listOf("long_sleeve_dress","long_sleeve_outer","long_sleeve_top","short_sleeve_dress","short_sleeve_outer","short_sleeve_top",
        "shorts","skirt","sling","sling_dress","trousers","vest","vest_dress")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage)
        sendImgName()

        chart = findViewById(R.id.pieChart)

        configChartView()

        edit_btn.setOnClickListener{
            val intent = Intent(this, MyPage_Edit::class.java)
            startActivity(intent)

        }

    }
    private fun configChartView() {

        val pie : Pie = AnyChart.pie()

        val dataPieChart: MutableList<DataEntry> = mutableListOf()

        for (index in salary.indices){
            dataPieChart.add(ValueDataEntry(month.elementAt(index),salary.elementAt(index)))
        }

        pie.data(dataPieChart)
//        pie.title("Salaries Overview")
        chart!!.setChart(pie)

    }

    private fun sendImgName() {
        //        Toast.makeText(mainActivity, "제대로 전송됨", Toast.LENGTH_LONG).show()
        thread {

            //이미지 이름을 url 뒤에 붙여 전달해줌
            var jsonobj = JSONObject()
            Log.d("bit_img_img", "이미지 이름 전송함")
            val url =
                "http://172.30.1.53:8000/recommend/mypiecategory/?id=" + userId  //장고 서버 주소..? 랑 뭘 넣어야하지? view 함수에 들어갈 ~

            //Okhttp3라이브러리의 OkHttpClient객체를 이요해서 작업
            val client = OkHttpClient()

            //json데이터를 이용해서 request 처리
            val jsondata = jsonobj.toString()
            //서버에 요청을 담당하는 객체
            val builder = Request.Builder()    // request객체를 만들어주는 객체 생성
            builder.url(url)                   //Builder객체에 request할 주소(네트워크상의 주소)셋팅
            builder.post(
                RequestBody.create(
                    MediaType.parse("application/json"),
                    jsondata
                )
            ) // 요청메시지 만들고 요청메시지의 타입이 json이라고 설정
            val myrequest: Request = builder.build() //Builder객체를 이용해서 request객체 만들기
            //생성한 request 객체를 이용해서 웹에 request하기 - request결과로 response 객체가 리턴
            // ==> Response가 서버에서 돌려준 josn 객체인가??
            val response: Response = client.newCall(myrequest).execute()

            //response에서 메시지꺼내서 로그 출력하기 -> 결과가 뭘로 오는지, 이미지 이름과 카테고리 분류된 결과가 오면 DB에 저장하는 코드 작성
            //결과를 받아와서 모델 객체를.. 만들어서? recycler View에 반영해줘야 함
            val result: String? = response.body()?.string()

            Log.d("http", result!!) //로그 찍어본 후에 파싱해서 옷 객체로 만들고, 리사이클러뷰에 띄우기

            val jsonObject = JSONObject(result.trimIndent())

            val jsonString = jsonObject.getString("n")
            val usernameJson = jsonObject.getString("username")
            val useremailJson = jsonObject.getString("useremail")

            Log.d("test", usernameJson)
            Log.d("test", useremailJson)

            var category_num = jsonString.substringAfter("[")
                .substringBeforeLast("]").split(",")


            for (i: Int in 0..12) {
                salary.add(category_num[i].toInt())
            }
            Log.d("test", salary.toString())

            runOnUiThread {
                //여기서 리사이클러뷰를 바꿔줘야 하나?
                mypage_username.setText(usernameJson.toString())
                mypage_useremail.setText(useremailJson.toString())
                Log.d("bit_img_img", "여기까지 넘어옴")


            }
        }

    }
}

