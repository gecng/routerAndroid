package com.example.routerdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gecng.routeannotation.Route
import com.gecng.routerlib.SRouter
import com.gecng.routerlib.interceptor.IInterceptor
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "app/mainAct",interceptors = ["x1","x2"])
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvHello.setOnClickListener {
            SRouter.INSTANCE.context(this@MainActivity)
                .path("sec/main")
                .keyAndValue("name","android")
                .keyAndValue("arg1", "tried")
                .withInterceptor { lists ->
                    lists.add(object : IInterceptor {
                        override fun check(): Boolean {
                            return true
                        }

                        override fun redirect() {
                        }

                    })
                    return@withInterceptor lists
                }.route()
        }


    }
}
