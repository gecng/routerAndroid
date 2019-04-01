package com.gecng.routerlib.interceptor

abstract class AbsInterceptor(private var nextInterceptor: IInterceptor?) : IInterceptor {


    /**
     * 检查是否 拦截
     * true 拦截
     * false 不拦截
     */
    final override fun check(): Boolean {
        if (!intercept()) {
            return nextInterceptor?.check() ?: false
        }
        return true
    }

    /**
     * 检查是否 拦截
     * true 拦截
     * false 不拦截
     */
    abstract fun intercept(): Boolean
}