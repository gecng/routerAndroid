package com.h4h4da.secondmodule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.gecng.routeannotation.Route
import com.gecng.routerlib.SRouter
import com.gecng.routerlib.build
import kotlinx.android.synthetic.main.sec_activity_main.*

@Route(path = "sec/main", interceptors = ["login"])
class SecModuleActOne : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.sec_activity_main)

        jump2Third.setOnClickListener {
            SRouter.INSTANCE.build("sec/third").route()
        }


        Log.d("sec", intent.getStringExtra("name"))

        Log.d("sec", intent.getStringExtra("arg1"))
    }


}