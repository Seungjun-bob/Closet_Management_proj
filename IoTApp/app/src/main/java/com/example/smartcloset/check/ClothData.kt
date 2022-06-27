package com.example.smartcloset.check

import android.graphics.Bitmap

class ClothData (val mycategory: String, val mycolor: String, val buydate: String, val myimg: Bitmap){
    override fun toString(): String {
        return "$mycategory, $mycolor, $buydate, $myimg"
    }
}
