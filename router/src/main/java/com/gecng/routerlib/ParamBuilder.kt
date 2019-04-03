package com.gecng.routerlib

import android.content.Context
import android.os.Bundle

class ParamBuilder {

    var path: String? = null
    var bundle: Bundle? = null

    var context: Context? = null


    fun path(path: String) = apply {
        this.path = path
    }

//
//    fun withInterceptor(with: (LinkedList<IInterceptor>) -> LinkedList<IInterceptor>) =
//        apply {
//            if (interceptors === null) {
//                interceptors = LinkedList()
//            }
//            interceptors = with(interceptors!!)
//
//        }


    fun keyAndValue(key: String, value: Any) = apply {
        if (bundle === null) {
            bundle = Bundle()
        }
        when (value) {
            is String -> {
                bundle!!.putString(key, value)
            }
            is Boolean -> {
                bundle!!.putBoolean(key, value)
            }
            is Int -> {
                bundle!!.putInt(key, value)
            }
        }
    }


    fun route() {
        SRouter.INSTANCE.route(this)
    }

}