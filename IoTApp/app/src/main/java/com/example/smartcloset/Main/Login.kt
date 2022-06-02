package com.example.smartcloset.Main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.smartcloset.R

class Login: Fragment {
    lateinit var content:String
    constructor(content:String){
        this.content = content
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { //View가 만들어질 때 login.xml로 화면을 만들어 넘겨줌
        return inflater.inflate(R.layout.login, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}