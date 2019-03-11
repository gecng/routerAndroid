package com.gecng.router

import com.example.routerdemo.MainActivity
import com.example.routerdemo.SecondAct
import com.gecng.routeannotation.RouterInfo
import java.util.LinkedHashMap
import kotlin.String

class ModuleRouterTable {
    fun register(): LinkedHashMap<String, RouterInfo> {
        val routeMap = LinkedHashMap<String,RouterInfo>()
        routeMap["app/mainAct"] = RouterInfo("app/mainAct", MainActivity::class.java)
        routeMap["app/second"] = RouterInfo("app/second", SecondAct::class.java)
        return routeMap
    }
}
