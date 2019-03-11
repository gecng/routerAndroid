package com.gecng.routeannotation

import java.util.*

/**
 * 映射关系，url 所对应的 activity
 */
data class RouterInfo(val path: String, val clazz: Class<*>) {
    //拦截器  针对某个url
    var intercepters: LinkedList<Class<*>>? = null

}