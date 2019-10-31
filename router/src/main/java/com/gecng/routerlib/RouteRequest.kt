package com.gecng.routerlib

import android.content.Context
import android.content.Intent
import android.os.Bundle

data class RouteRequest(
    var context: Context? = null,
    var path: String,
    var intent: Intent,
    var callback: RouteCallback? = null
) {

    fun recycle() {
        context = null
        callback = null
    }

}