package com.example.routerdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gecng.routeannotation.Route
import com.gecng.routerlib.SRouter
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "app/mainAct")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvHello.setOnClickListener {
            jump2Other()
        }
        update()
        increase.setOnClickListener {
            UserManager.increase()
            update()
        }

        zero.setOnClickListener {
            UserManager.zero()
            update()
        }


    }

    private fun jump2Other() {
        SRouter.INSTANCE.context(this@MainActivity)
            .path("sec/main")
            .keyAndValue("name", "android")
            .keyAndValue("arg1", "tried").route()
    }


    private fun update() {
        tvNum.text = "当前num ${UserManager.id}"
    }
}
