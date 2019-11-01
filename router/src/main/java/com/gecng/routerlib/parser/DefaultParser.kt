package com.gecng.routerlib.parser

import android.content.Intent
import com.gecng.routerlib.RouteRequestBody

class DefaultParser : IParser {

    override fun parseParams(body: RouteRequestBody): Intent {
        return body.getIntent()
    }

    override fun check(body: RouteRequestBody): Boolean {
        return true
    }

    override fun getPath(body: RouteRequestBody): String {
        return body.getPath()
    }
}