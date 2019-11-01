package com.h4h4da.secondmodule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gecng.routeannotation.Route
import com.gecng.routerlib.activitymanager.ActivityStackManager
import kotlinx.android.synthetic.main.sec_activity_third.*

@Route(path = "sec/third")
class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sec_activity_third)
        jump2Main.setOnClickListener {
            ActivityStackManager.INSTANCE.popUntil("app/mainAct", false)
        }
    }
}