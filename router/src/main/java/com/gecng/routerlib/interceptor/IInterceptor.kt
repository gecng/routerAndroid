package com.gecng.routerlib.interceptor

import com.gecng.routerlib.RouteRequest

interface IInterceptor {
    /**
     * 判断是否拦截
     * true -》 被拦截了
     * false -》 不拦截
     */
    fun onIntercept(routeRequest: RouteRequest): Boolean

}