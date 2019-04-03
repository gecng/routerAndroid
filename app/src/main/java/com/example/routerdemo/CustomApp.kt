package com.example.routerdemo

import android.app.Application
import com.gecng.routerlib.SRouter

class CustomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SRouter.INSTANCE.init(listOf("app","second"))
    }
}