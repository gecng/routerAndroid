package com.example.routerdemo

import android.util.Log
import com.gecng.routerlib.interceptor.IInterceptor
import com.gecng.routeannotation.Interceptor
import com.gecng.routerlib.RouteRequest

@Interceptor(name = "login")
class LoginInterceptor : IInterceptor {
    override fun onIntercept(routeRequest: RouteRequest): Boolean {
        val needIntercept = UserManager.id != 0
        if (needIntercept) Log.d("Login", "被拦截了")
        return needIntercept
    }

}
