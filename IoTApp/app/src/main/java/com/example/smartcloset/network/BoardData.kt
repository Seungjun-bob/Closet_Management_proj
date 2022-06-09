package com.example.smartcloset.network

//JSONObject를 표현한 객체로 파이썬의 models.py와 동일한 역할
class BoardData(var boardNo:Int, var title:String, var content:String, var writer:String, var write_date:String) {
    override fun toString(): String {
        return "$boardNo, $title, $content, $writer, $write_date"
    }
}