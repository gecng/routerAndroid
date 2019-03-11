package com.gecng.routerlib.interceptor

interface IInterceptor {
    fun check(): Boolean

    fun redirect()
}