package com.gecng.routerlib

import android.content.Context
import android.content.Intent

class ParamBuilder(var path: String) {

    private var mIntent: Intent? = null

    fun setValue(key: String, value: Any) = apply {

        if (mIntent === null) {
            mIntent = Intent()
        }
        val i = mIntent!!
        when (value) {
            is String -> i.putExtra(key, value)
            is Boolean -> i.putExtra(key, value)
            is Int -> i.putExtra(key, value)
            is Long -> i.putExtra(key, value)
            is Char -> i.putExtra(key, value)
        }
    }


    fun route() {
        val body = RouteRequestBody()
        body.apply {
            setPath(path)
            if (mIntent != null) {
                setIntent(mIntent!!)
            }
        }
        SRouter.INSTANCE.route(body)
    }

    fun route(context: Context) {}
}