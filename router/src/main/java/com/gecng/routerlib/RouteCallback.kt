package com.gecng.routerlib

interface RouteCallback {
    fun routeFinished()

    fun onIntercepted()

    fun notFound()
}