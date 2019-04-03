package com.gecng.routeannotation

data class InterceptorInfo(val path: String, val clazz: Class<*>) {
    var interceptor: IInterceptor? = null
}