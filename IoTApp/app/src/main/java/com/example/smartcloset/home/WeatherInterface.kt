package com.example.smartcloset.home

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// 결과 xml 파일에 접근해서 정보 가져오기
interface WeatherInterface {
    // getUltraSrtFcst : 초단기 예보 조회 + 인증키
    @GET("getUltraSrtNcst?serviceKey=AlUCFS55oaHO3YecssapG9Hf0CTFp23Roj27VnlP3ne7Yr34sWeieyrzDTEb1vGbzvjcQhig%2FXjmo1EjP2a1GQ%3D%3D")

    fun GetWeather(@Query("numOfRows") num_of_rows : Int,   // 한 페이지 경과 수
                   @Query("pageNo") page_no : Int,          // 페이지 번호
                   @Query("dataType") data_type : String,   // 응답 자료 형식
                   @Query("base_date") base_date : String,  // 발표 일자
                   @Query("base_time") base_time : String,  // 발표 시각
                   @Query("nx") nx : String,                // 예보지점 X 좌표
                   @Query("ny") ny : String)                // 예보지점 Y 좌표
            : Call<WEATHER>
}


// xml 파일 형식을 data class로 구현
data class WEATHER (val response : RESPONSE)
data class RESPONSE(val header : HEADER, val body : BODY)
data class HEADER(val resultCode : Int, val resultMsg : String)
data class BODY(val dataType : String, val items : ITEMS, val totalCount : Int)
data class ITEMS(val item : List<ITEM>)
// category : 자료 구분 코드, fcstDate : 예측 날짜, fcstTime : 예측 시간, fcstValue : 예보 값
data class ITEM(val category : String, val fcstDate : String, val fcstTime : String, val fcstValue : String)

// retrofit을 사용하기 위한 빌더 생성
private val retrofit = Retrofit.Builder()
    .baseUrl("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object ApiObject {
    val retrofitService: WeatherInterface by lazy {
        retrofit.create(WeatherInterface::class.java)
    }
}