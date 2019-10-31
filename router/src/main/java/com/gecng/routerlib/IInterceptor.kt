package com.gecng.routerlib

interface IInterceptor {
    /**
     * 判断是否拦截
     * true -》 被拦截了
     * false -》 不拦截
     */
    fun onIntercept(routeRequest: RouteRequest): Boolean

}