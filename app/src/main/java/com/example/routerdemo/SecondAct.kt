package com.example.routerdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gecng.routeannotation.Route
import com.gecng.routerlib.SRouter
import com.gecng.routerlib.build
import kotlinx.android.synthetic.main.activity_second.*


@Route(path = "app/second", interceptors = ["app/loginInterceptor"])
class SecondAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        jump2Third.setOnClickListener {
            SRouter.INSTANCE.build("sec/third").route()
        }
    }

}