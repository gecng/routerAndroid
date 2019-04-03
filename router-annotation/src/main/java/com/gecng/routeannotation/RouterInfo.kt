package com.gecng.routeannotation

/**
 * 映射关系，url 所对应的 activity ， 以及相关的拦截器
 */
data class RouterInfo(val path: String, val clazz: Class<*>) {
    var interceptors: ArrayList<String>? = null
}