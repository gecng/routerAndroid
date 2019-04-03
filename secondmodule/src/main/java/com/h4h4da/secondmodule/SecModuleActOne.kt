package com.h4h4da.secondmodule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gecng.routeannotation.Route

@Route(path = "sec/main", interceptors = ["app/login"])
class SecModuleActOne : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.sec_activity_main)
    }


}