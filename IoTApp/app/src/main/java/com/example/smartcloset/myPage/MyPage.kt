package com.example.smartcloset.myPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.mypage.*

class MyPage : AppCompatActivity() {
    private var chart: AnyChartView? = null

    private val salary = listOf(200,300,400,600)
    private val month = listOf("T-shirts","Vest","Pants","Skirt")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage)

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
        pie.title("Salaries Overview")
        chart!!.setChart(pie)

    }
}