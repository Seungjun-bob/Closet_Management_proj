package com.example.smartcloset.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcloset.home.ModelWeather
import com.example.smartcloset.R

class WeatherAdapter (var items : Array<ModelWeather>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    // 뷰 홀더 만들어서 반환, 뷰릐 레이아웃은 list_item_weather.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_weather_item, parent, false)
        return ViewHolder(itemView)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: WeatherAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    // 아이템 갯수 리턴
    override fun getItemCount() = items.count()

    // 뷰 홀더 설정
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun setItem(item : ModelWeather) {
            val tvTime = itemView.findViewById<TextView>(R.id.tvTime)           // 시각
            val tvRainType = itemView.findViewById<TextView>(R.id.tvRainType)   // 강수 형태
            val tvHumidity = itemView.findViewById<TextView>(R.id.tvHumidity)   // 습도
            val tvSky = itemView.findViewById<TextView>(R.id.tvSky)             // 하늘 상태
            val tvTemp = itemView.findViewById<TextView>(R.id.tvTemp)           // 온도
            val tvRecommends = itemView.findViewById<TextView>(R.id.tvRecommends)   // 옷 추천

            tvTime.text = item.fcstTime
            tvRainType.text = getRainType(item.rainType)
            tvHumidity.text = item.humidity
            tvSky.text = getSky(item.sky)
            tvTemp.text = item.temp + "°"
            tvRecommends.text = getRecommends(item.temp.toInt())
        }
    }

    // 강수 형태
    fun getRainType(rainType : String) : String {
        return when(rainType) {
            "0" -> "없음"
            "1" -> "비"
            "2" -> "비/눈"
            "3" -> "눈"
            else -> "오류 rainType : " + rainType
        }
    }

    // 하늘 상태
    fun getSky(sky : String) : String {
        return when(sky) {
            "1" -> "맑음"
            "3" -> "구름 많음"
            "4" -> "흐림"
            else -> "오류 rainType : " + sky
        }
    }

    // 옷 추천
    fun getRecommends(temp : Int) : String{
        return when (temp) {
            in 5..8 -> "울 코트, 가죽 옷, 기모"
            in 9..11 -> "트렌치 코트, 야상, 점퍼"
            in 12..16 -> "자켓, 가디건, 청자켓"
            in 17..19 -> "니트, 맨투맨, 후드, 긴바지"
            in 20..22 -> "블라우스, 긴팔 티, 슬랙스"
            in 23..27 -> "얇은 셔츠, 반바지, 면바지"
            in 28..50 -> "민소매, 반바지, 린넨 옷"
            else -> "패딩, 누빔 옷, 목도리"
        }
    }
}