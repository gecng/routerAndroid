package com.gecng.routerlib

import android.content.Context
import android.content.Intent
import com.gecng.routeannotation.RouterInfo

/**
 * Simple Router
 */
class SRouter {

    companion object {
        val INSTANCE: SRouter by lazy {
            SRouter()
        }
    }

    //存放全部的路由信息
    val map: LinkedHashMap<String, RouterInfo> = LinkedHashMap()

    var moduleList: List<String>? = null

    //全局拦截器，全局拦截器，对所有的路由进行拦截
    val globalIncepter: List<RouteInterceptor> = listOf()
    // todo 是否需要添加module 级别的拦截器，对该module 级别下的路由进行拦截


    fun init() {
        map.putAll(ModuleTableCollector.collect(listOf("app")))
    }


    fun context(context: Context): ParamBuilder {
        return ParamBuilder().apply { this.context = context }
    }

    fun route(paramBuilder: ParamBuilder) {

        val path = paramBuilder.path
        val routerInfo = map[path]
        if (path.isNullOrBlank()) {
            return
        }
        if (routerInfo === null) {
            return
        }

        val intent = Intent()
        intent.setClass(paramBuilder.context!!, routerInfo.clazz)
        if (paramBuilder.bundle != null) {
            intent.putExtras(paramBuilder.bundle!!)
        }
        paramBuilder.context!!.startActivity(intent)


    }


    fun routeWithResult(paramBuilder: ParamBuilder) {


    }


}