package com.example.routerdemo

import com.gecng.routerlib.IInterceptor
import com.gecng.routeannotation.Interceptor
import com.gecng.routerlib.RouteRequest

@Interceptor(name = "app/login")
class LoginInterceptor : IInterceptor {
    override fun onIntercept(routeRequest: RouteRequest): Boolean {
        return UserManager.id != 0
    }

//    override fun onIntercept(): Boolean {
//
//    }
}
