package com.gecng.routerlib

import android.content.Context
import android.content.Intent
import com.gecng.routeannotation.RouterInfo
import com.gecng.routerlib.collector.ModuleTableCollector
import com.gecng.routerlib.interceptor.InterceptorManager

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
    private val routeMap: LinkedHashMap<String, RouterInfo> = LinkedHashMap()

    private val interceptorManager: InterceptorManager = InterceptorManager()


    fun init(moduleList: List<String>) {
        routeMap.putAll(ModuleTableCollector.collect(moduleList))
        interceptorManager.init(moduleList)
    }


    fun context(context: Context): ParamBuilder {
        return ParamBuilder().apply { this.context = context }
    }

    fun route(paramBuilder: ParamBuilder) {

        val path = paramBuilder.path
        val routerInfo = routeMap[path]
        if (path.isNullOrBlank()) {
            return
        }
        if (routerInfo === null) {
            return
        }
        if (interceptorManager.onIntercept(routerInfo.interceptors)) {
            //todo 被拦截了
        } else {
            // todo 没有被拦截 进行跳转

            val intent = Intent()
            intent.setClass(paramBuilder.context!!, routerInfo.clazz)
            if (paramBuilder.bundle != null) {
                intent.putExtras(paramBuilder.bundle!!)
            }
            paramBuilder.context!!.startActivity(intent)
        }


    }


    fun routeWithResult(paramBuilder: ParamBuilder) {


    }


}